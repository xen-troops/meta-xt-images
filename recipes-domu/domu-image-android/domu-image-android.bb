SUMMARY = "Base for Android images"

LICENSE = "MIT"

inherit build_yocto
inherit xt_quirks
inherit xt_reconstruct

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
FILESEXTRAPATHS_prepend := "${THISDIR}/../../classes:"

S = "${WORKDIR}/repo"

################################################################################
# set to AUTOREV so we can override this while reconstructing this build with
# specific commit ID
################################################################################
SRCREV = "${AUTOREV}"

###############################################################################
# extra layers and files to be put after Yocto's do_unpack into inner builder
###############################################################################
# these will be populated into the inner build system on do_unpack_xt_extras
XT_QUIRK_UNPACK_SRC_URI += " \
    file://meta-xt-images-extra;subdir=repo \
    file://xt_reconstruct.bbclass;subdir=repo/meta-xt-images-extra/classes \
"

# these layers will be added to bblayers.conf on do_configure
XT_QUIRK_BB_ADD_LAYER += " \
    meta-xt-images-extra \
"

SRC_URI_append = " \
    file://0001-fetch2-repo-Always-check-if-branch-is-correct.patch;patchdir=poky \
    file://0002-fetch2-repo-Make-fetcher-always-sync-on-unpack.patch;patchdir=poky \
    file://0003-fetch2-repo-Use-multiple-jobs-to-fetch-and-sync.patch;patchdir=poky \
    file://0004-Make-pack-unpack-multithreaded.patch;patchdir=poky \
    file://0005-fetch2-repo-Add-group-parameter.patch;patchdir=poky \
"

update_local_conf() {
    local local_conf="${S}/build/conf/local.conf"

    cd ${S}

    # Android uses its own machine for its build, but we need to define
    # some machine related settings that will be used by Yocto to create paths.
    # Pay attention that this function makes weak assignment (??=).
    # You can override this value by your own 'hard' assignment (=)
    # in local.conf.domu-image-android for your product.
    base_set_weak_conf_value ${local_conf} MACHINE "qemux86-64"

    if [ ! -z ${XT_ANDROID_PREBUILDS_DIR} ];then
        base_set_conf_value ${local_conf} DDK_KM_PREBUILT_MODULE "${XT_ANDROID_PREBUILDS_DIR}/pvr-km/pvrsrvkm.ko"
        base_set_conf_value ${local_conf} DDK_UM_PREBUILDS "${XT_ANDROID_PREBUILDS_DIR}/pvr-um/"
    fi
}

python do_configure_append() {
    bb.build.exec_func("update_local_conf", d)
}

# we do not have an SDK to populate
do_populate_sdk() {
}

# populate Android ccache as well
do_populate_sstate_cache_append() {
    if [ -n "${EXPANDED_XT_POPULATE_SSTATE_CACHE}" ] ; then
        CURRENT_CCACHE=$(ls ${SSTATE_DIR}/ | grep "ccache")
        if [ -n "${CURRENT_CCACHE}" ] && [ -d ${SSTATE_DIR}/${CURRENT_CCACHE} ]; then
            if [ -d ${XT_SSTATE_CACHE_MIRROR_DIR}/${CURRENT_CCACHE} ]; then
                rm -rf  ${XT_SSTATE_CACHE_MIRROR_DIR}/${CURRENT_CCACHE}
            fi
            mv -f ${SSTATE_DIR}/${CURRENT_CCACHE} ${XT_SSTATE_CACHE_MIRROR_DIR}/ || true
        fi
    fi
}
