SUMMARY = "Image for driver domain"

require inc/domx-image-weston.inc

# these layers will be added to bblayers.conf on do_configure
XT_QUIRK_BB_ADD_LAYER += " \
    meta-arm/meta-arm/recipes-sequrity \
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
