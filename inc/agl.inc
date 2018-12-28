SUMMARY = "Base for AGL images"

LICENSE = "MIT"

inherit build_yocto
inherit xt_quirks

S = "${WORKDIR}/repo"

# path for machine non-specific recipes
FILESEXTRAPATHS_prepend := "${THISDIR}/../recipes-domx:"

XT_QUIRK_UNPACK_SRC_URI += " \
    file://meta-xt-images-domx;subdir=repo \
"

# these layers will be added to bblayers.conf on do_configure
XT_QUIRK_BB_ADD_LAYER += " \
    meta-virtualization \
    meta-selinux \
    meta-clang \
    meta-xt-images-domx \
"

# Set default revision of AGL auxiliary layers
BRANCH ?= "morty"

SRC_URI_append = " \
    git://git.yoctoproject.org/meta-virtualization;destsuffix=repo/meta-virtualization;branch=${BRANCH} \
    git://git.yoctoproject.org/meta-selinux;destsuffix=repo/meta-selinux;branch=${BRANCH} \
    git://github.com/kraj/meta-clang.git;destsuffix=repo/meta-clang;branch=${BRANCH} \
"

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
