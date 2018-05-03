require xt_rcar.inc

# path for machine specific layers and recipes
FILESEXTRAPATHS_prepend := "${THISDIR}/../machine:"

XT_QUIRK_UNPACK_SRC_URI += " \
    file://meta-xt-images-vgpu;subdir=repo \
"

# these layers will be added to bblayers.conf on do_configure
XT_QUIRK_BB_ADD_LAYER += " \
    meta-virtualization \
    meta-xt-images-vgpu \
"

################################################################################
# Renesas R-Car
################################################################################

XT_QUIRK_UNPACK_SRC_URI_append_rcar = "\
    file://meta-xt-images-rcar-gen3;subdir=repo \
"

# these layers will be added to bblayers.conf on do_configure
XT_QUIRK_BB_ADD_LAYER_append_rcar = "\
    meta-xt-images-rcar-gen3 \
"

