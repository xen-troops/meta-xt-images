SUMMARY = "Android"
DESCRIPTION = "Recipe for building Android image"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

S = "${WORKDIR}/repo"

SRCREV = "${AUTOREV}"

require inc/xt_shared_env.inc

inherit python3native
inherit xt_reconstruct

# remove all depends normally required for oe_make, e.g. cross-compiler
# and tools: these are not needed for Android
BASEDEPENDS = ""

DEPENDS += "lz4-native bc-native python3-pycrypto-native curl-native rsync-native bison-native coreutils-native unzip-native zip-native"

ANDROID_PRODUCT ?= "aosp_arm64"
ANDROID_VARIANT ?= "eng"
SOC_FAMILY ?= "default"

ANDROID_OUT_DIR_COMMON_BASE = ""

python __anonymous () {
    prebuilt_vars = ["DDK_KM_PREBUILT_MODULE", "TARGET_PREBUILT_KERNEL", "DDK_UM_PREBUILDS"]
    for var in prebuilt_vars:
        if d.getVar(var, True) != None:
            if not os.path.exists(d.getVar(var, True)):
                raise bb.parse.SkipPackage('%s points to a non-existent path' % (var))
            d.appendVar("DDK_PREBUILT_VARS", "%s=%s\n" % (var, d.getVar(var, True)))
}

do_configure() {
}

do_compile() {
    cd ${S}
    USRBINPATH_NATIVE="${RECIPE_SYSROOT_NATIVE}/usr/bin"

    # run Android build in sane environment
    env -i HOME="$HOME" LC_CTYPE="${LC_ALL:-${LC_CTYPE:-$LANG}}" USER="$USER" \
           PATH="${USRBINPATH_NATIVE}:${PATH}" \
           OUT_DIR_COMMON_BASE="${ANDROID_OUT_DIR_COMMON_BASE}" \
           ${DDK_PREBUILT_VARS} \
           bash -c "source build/envsetup.sh && \
                    lunch ${ANDROID_PRODUCT}-${ANDROID_VARIANT} && \
                    make ${EXTRA_OEMAKE} ${PARALLEL_MAKE} \
           "
}
