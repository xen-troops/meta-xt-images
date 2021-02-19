SUMMARY = "Image with weston for driver domain"

require inc/domx-image-weston.inc

XT_QUIRK_UNPACK_SRC_URI += " \
    file://meta-xt-images-vgpu;subdir=repo \
"

# these layers will be added to bblayers.conf on do_configure
XT_QUIRK_BB_ADD_LAYER += " \
    meta-xt-images-vgpu \
    meta-arm/meta-arm \
"

################################################################################
# Renesas R-Car
################################################################################
FILESEXTRAPATHS_prepend_rcar := "${THISDIR}/files_rcar:"

XT_QUIRK_UNPACK_SRC_URI_append_rcar = "\
    file://meta-xt-images-extra;subdir=repo \
"

# these layers will be added to bblayers.conf on do_configure
XT_QUIRK_BB_ADD_LAYER_append_rcar = "\
    meta-xt-images-extra \
"
