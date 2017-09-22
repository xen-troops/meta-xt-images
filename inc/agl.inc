SUMMARY = "Base for AGL images"

LICENSE = "MIT"

inherit build_yocto
inherit xt_quirks
require xt_rcar.inc

S = "${WORKDIR}/repo"
# path for machine specific layers and recipes
FILESEXTRAPATHS_prepend := "${THISDIR}/../machine:"

# path for machine non-specific recipes
FILESEXTRAPATHS_prepend := "${THISDIR}/../recipes-domx:"

XT_QUIRK_UNPACK_SRC_URI += "file://meta-xt-images-domx;subdir=repo"

# these layers will be added to bblayers.conf on do_configure
XT_QUIRK_BB_ADD_LAYER += " meta-xt-images-domx"

################################################################################
# set to AUTOREV so we can override this while reconstructing this build with
# specific commit ID
################################################################################
SRCREV = "${AUTOREV}"

################################################################################
# AGL has its own setup script which calls poky's one internally
################################################################################
XT_AGL_FEATURES = "agl-appfw-smack"

XT_BB_CONFIG_CMD = "source meta-agl/scripts/aglsetup.sh -m ${MACHINE}-xt ${XT_AGL_FEATURES}"
XT_BB_RUN_CMD = "source meta-agl/scripts/aglsetup.sh -m ${MACHINE}-xt ${XT_AGL_FEATURES}"

################################################################################
# All repo:// URLs must have "scmdata=keep" parameter, so we can build history
################################################################################

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
