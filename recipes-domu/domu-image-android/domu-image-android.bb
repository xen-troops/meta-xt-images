SUMMARY = "Base for Android build"

LICENSE = "MIT"

require inc/xt_shared_env.inc

# Important note.
# We use REPODIR and SRC_URI with 'directpath=1' to bypass fetcher's
# modification of download path. This is done for performance reasons.
#
# 'Out-of-stock' repo fetcher gets sources into ${DL_DIR}/repo/xxx folder,
# then tar/gzip them and then ungzip to ${S}.
# These steps add almost one hour to fetching on PC.
#
# With modifications to xt-distro's repo fetcher (adding 'depth' and 'directpath'
# parameters to SRC_URI) and using REPODIR instead of DL_DIR (to avoid copying),
# we have do_fetch() completed within 20 minutes.
#
# Some details:
# 'depth=1' reduces amount of data downloaded from server, and thus reduces fetch time
#           in half: ~(10-14)min instead of ~23min
# 'REPODIR' is used to download sources directly into required place,
#           and to avoid copying of tens of gigabytes from ${DL_DIR}
# 'directpath=1' is used to suppress creation of subfolders to ${REPODIR}.
#           This is required for correct work of do_compile() with downloaded sources.

ANDROID_SOURCES = "repo://github.com/xen-troops/android_manifest;protocol=https;branch=android-11-master;manifest=doma.xml;depth=1;directpath=1"

python __anonymous () {
    # If we are provided with existing android sources then no fetching is required
    if d.getVar("XT_EXTERNAL_ANDROID_SOURCES", expand=True):
        # We do not use REPODIR in this case
        d.setVar("S", "${XT_EXTERNAL_ANDROID_SOURCES}")
        d.setVarFlag("do_fetch", "noexec", "1")
    else:
        d.setVar("REPODIR", "${SSTATE_DIR}/../android_sources")
        d.setVar("S", "${REPODIR}")

    # We either use proprietary sources or use prebuilt graphics
    if d.getVar("XT_ANDROID_PREBUILDS_DIR", expand=True):
        DDK_KM_PREBUILT_MODULE = "${XT_ANDROID_PREBUILDS_DIR}/pvr-km/pvrsrvkm.ko"
        DDK_UM_PREBUILDS = "${XT_ANDROID_PREBUILDS_DIR}/pvr-um/"
    else:
        # groups=all results in fetching of proprietary projects
        d.appendVar("ANDROID_SOURCES", ";groups=all")

    prebuilt_vars = ["DDK_KM_PREBUILT_MODULE", "DDK_UM_PREBUILDS", "TARGET_PREBUILT_KERNEL"]
    for var in prebuilt_vars:
        if d.getVar(var, True) != None:
            if not os.path.exists(d.getVar(var, True)):
                raise bb.parse.SkipPackage('%s points to a non-existent path' % (var))
            d.appendVar("PREBUILT_VARS", "%s=%s\n" % (var, d.getVar(var, True)))
}

SRC_URI = "${ANDROID_SOURCES}"

# SOC_FAMILY is required to be set for proper build of optee, graphic and output folder name.
# See product's append for properly set value
SOC_FAMILY ?= "SOC_FAMILY_is_not_set"

# SOC_REVISION is used only for build of graphic drivers.
# See product's append for possible (but not mandatory) override
SOC_REVISION = ""


# Product and variant are used to form string for 'lunch xenvm-userdebug'
# These two may be redefined in bbappend-file of local.conf
ANDROID_PRODUCT ??= "xenvm"
ANDROID_VARIANT ??= "userdebug"


# Disable all not used tasks
# For android we use only
# - do_fetch   -- get sources (repo init && sync)
# - do_compile -- set android environment and build
# - do_install -- copy assembled images into deploy folder
# - do_build   -- default start point for build of domain
do_unpack[noexec] = "1"
do_patch[noexec] = "1"
do_configure[noexec] = "1"
do_populate_sdk[noexec] = "1"
do_populate_sysroot[noexec] = "1"
do_collect_build_history[noexec] = "1"
do_populate_sstate_cache[noexec] = "1"

# This is important workaround.
# Host python is used to build proper trusted application (TA) for OP-TEE.
# We can't use android's python because we can't install required library into it.
# So we have to provide host python with special library.
# Also pay attention that PYTHON is set by poky, and not need to be set in host
ANDROID_HOST_PYTHON="$(dirname ${PYTHON})"

EXTRA_OEMAKE_append = " \
    TARGET_BOARD_PLATFORM=${SOC_FAMILY} \
    TARGET_SOC_REVISION=${SOC_REVISION} \
    HOST_PYTHON=${ANDROID_HOST_PYTHON} \
"

# Let android build system clarify what need to be build
do_compile[nostamp] = "1"
do_compile() {
    cd ${S}
    USRBINPATH_NATIVE="${RECIPE_SYSROOT_NATIVE}/usr/bin"

    # run Android build in sane environment
    # Pay attention: EXTRA_OEMAKE includes multithread option '-j' (see poky sources)
    env -i HOME="$HOME" USER="$USER" \
           PATH="${USRBINPATH_NATIVE}:${PATH}" \
           ${PREBUILT_VARS} \
           bash -c "source build/envsetup.sh && \
                    lunch ${ANDROID_PRODUCT}-${ANDROID_VARIANT} && \
                    make ${EXTRA_OEMAKE} \
           "
}


# Always install latest android images
do_install[nostamp] = "1"
do_install() {
    install -d "${DEPLOY_DIR}/${BPN}/images"
    install -d "${XT_DIR_ABS_SHARED_BOOT_DOMA}"

    # copy images to the deploy directory
    find "${S}/out/target/product/${ANDROID_PRODUCT}" -maxdepth 1 -iname '*.img' -exec \
        cp -f --no-dereference --preserve=links {} "${DEPLOY_DIR}/${BPN}/images" \;

    # TODO assemble android's images into single image
}
