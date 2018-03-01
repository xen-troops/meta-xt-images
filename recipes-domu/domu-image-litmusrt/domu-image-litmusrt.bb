SUMMARY = "ARMV8 Generic LITMUS-RT guest domain"

LICENSE = "MIT"

inherit build_yocto
inherit xt_quirks

S = "${WORKDIR}/repo"

################################################################################
# set to AUTOREV so we can override this while reconstructing this build with
# specific commit ID
################################################################################
SRCREV = "${AUTOREV}"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

###############################################################################
# extra layers and files to be put after Yocto's do_unpack into inner builder
###############################################################################
XT_QUIRK_UNPACK_SRC_URI += "\
    file://meta-xt-images-extra;subdir=repo \
"

XT_QUIRK_BB_ADD_LAYER += " \
    meta-xt-images-extra \
"

