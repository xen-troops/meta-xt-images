SUMMARY = "Base for Fusion images"

LICENSE = "MIT"

inherit build_yocto
inherit xt_quirks

S = "${WORKDIR}/repo"

# path for machine non-specific recipes
FILESEXTRAPATHS_prepend := "${THISDIR}/../recipes-domx:"

###############################################################################
# extra layers and files to be put after Yocto's do_unpack into inner builder
###############################################################################
# these will be populated into the inner build system on do_unpack_xt_extras
XT_QUIRK_UNPACK_SRC_URI += " \
    file://meta-xt-images-extra;subdir=repo \
    file://meta-xt-images-domx;subdir=repo \
"

# these layers will be added to bblayers.conf on do_configure
XT_QUIRK_BB_ADD_LAYER += " \
    meta-xt-images-extra \
    meta-virtualization \
    meta-xt-images-domx \
"
BRANCH ?= "rocko"

SRC_URI_append = " \
    git://git.yoctoproject.org/meta-virtualization;destsuffix=repo/meta-virtualization;branch=${BRANCH} \
"
