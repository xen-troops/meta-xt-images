SUMMARY = "Provides disk image for DomA"

LICENSE = "MIT"

require recipes-domx/meta-xt-prod-domx/inc/xt_shared_env.inc

# This recipe has two purposes:
# - to be used as "domain wrapper" for android OS
# - assemble android's images into single disk image

# Disable all not used tasks
# For DomA we use only
# - do_install -- copy assembled images into deploy folder
# - do_build   -- default start point for build of domain
do_fetch[noexec] = "1"
do_compile[noexec] = "1"
do_unpack[noexec] = "1"
do_patch[noexec] = "1"
do_configure[noexec] = "1"
do_populate_sdk[noexec] = "1"
do_populate_sysroot[noexec] = "1"
do_collect_build_history[noexec] = "1"
do_populate_sstate_cache[noexec] = "1"

# Always run android's build and let it decide do we need rebuild or not
do_install[depends] = "android-os:do_install"
do_install[nostamp] = "1"
do_install() {
    install -d "${DEPLOY_DIR}/${BPN}/images"

    # TODO assemble android's images from
    # ${XT_DIR_ABS_SHARED_BOOT_DOMA}/android-os/*.img
    # into single image
    # ${DEPLOY_DIR}/${BPN}/images/domu-image-android.img
}
