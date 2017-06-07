SUMMARY = "Image with weston"

LICENSE = "MIT"

inherit build_yocto
inherit xt_quirks
require inc/xt_rcar.inc

S = "${WORKDIR}/repo"
# path for machine specific layers and recipes
FILESEXTRAPATHS_prepend := "${THISDIR}/../../machine:"

################################################################################
# set to AUTOREV so we can override this while reconstructing this build with
# specific commit ID
################################################################################
SRCREV = "${AUTOREV}"

################################################################################
# All repo:// URLs must have "scmdata=keep" parameter, so we can build history
################################################################################

################################################################################
# Renesas R-Car
################################################################################
SRC_URI_rcar = "repo://github.com/xen-troops/manifests;protocol=https;branch=vgpu-dev;scmdata=keep"
XT_QUIRK_PATCH_SRC_URI_rcar = "file://${S}/meta-renesas/meta-rcar-gen3/docs/sample/patch/patch-for-linaro-gcc/0001-rcar-gen3-add-readme-for-building-with-Linaro-Gcc.patch;patchdir=meta-renesas"
XT_BB_LAYERS_FILE_rcar = "meta-rcar-gen3/doc/bblayers.conf"
XT_BB_LOCAL_CONF_FILE_rcar = "meta-rcar-gen3/doc/local-wayland.conf"
XT_BB_IMAGE_TARGET_rcar = "core-image-weston"

FILESEXTRAPATHS_prepend_rcar := "${THISDIR}/files_rcar:"
# N.B. inner layer needs access to "inc" folder which is at its top
FILESEXTRAPATHS_prepend_rcar := "${THISDIR}/files_rcar/meta-xt-images-extra:"

XT_QUIRK_UNPACK_SRC_URI_append_rcar = "\
    file://meta-rcar-gen3;subdir=repo \
    file://meta-xt-images-extra;subdir=repo \
"

# these layers will be added to bblayers.conf on do_configure
XT_QUIRK_BB_ADD_LAYER_append_rcar = "\
    meta-rcar-gen3 \
    meta-xt-images-extra \
"

python do_unpack_append_rcar() {
    bb.build.exec_func('rcar_unpack_evaproprietary', d)
}

################################################################################
# Generic
################################################################################
XT_BB_IMAGE_TARGET = "core-image-weston"
