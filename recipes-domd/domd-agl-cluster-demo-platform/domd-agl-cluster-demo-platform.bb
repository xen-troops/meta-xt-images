require inc/agl.inc
require inc/agl_domd.inc

XT_AGL_FEATURES += "agl-cluster-demo"

XT_BB_IMAGE_TARGET = "agl-cluster-demo-platform"

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
