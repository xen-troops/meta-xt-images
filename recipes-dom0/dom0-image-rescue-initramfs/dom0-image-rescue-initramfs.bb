SUMMARY = "Minimalistic initrafs image for system rescue"

LICENSE = "MIT"

inherit build_yocto
inherit xt_quirks
inherit xt_reconstruct

S = "${WORKDIR}/repo"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
FILESEXTRAPATHS_prepend := "${THISDIR}/../../machine:"
FILESEXTRAPATHS_prepend := "${THISDIR}/../../recipes-domx:"

###############################################################################
# extra layers and files to be put after Yocto's do_unpack into inner builder
###############################################################################
# these will be populated into the inner build system on do_unpack_xt_extras
XT_QUIRK_UNPACK_SRC_URI += " \
    file://meta-xt-images-generic-armv8;subdir=repo \
    file://meta-xt-images-extra;subdir=repo \
    file://meta-xt-images-domx;subdir=repo \
"

# these layers will be added to bblayers.conf on do_configure
XT_QUIRK_BB_ADD_LAYER += " \
    meta-xt-images-generic-armv8 \
    meta-xt-images-extra \
    meta-xt-images-domx \
"

################################################################################
# set to AUTOREV so we can override this while reconstructing this build with
# specific commit ID
################################################################################
SRCREV = "${AUTOREV}"

configure_versions_base() {
    local local_conf="${S}/build/conf/local.conf"

    cd ${S}
    # override what xt-distro wants as machine as we don't depend on HW
    base_update_conf_value ${local_conf} MACHINE "generic-armv8-xt"
    base_update_conf_value ${local_conf} "PREFERRED_PROVIDER_virtual/kernel" "linux-generic-armv8"
}

python do_configure_append() {
    bb.build.exec_func("configure_versions_base", d)
}

