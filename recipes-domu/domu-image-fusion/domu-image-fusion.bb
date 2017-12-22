require inc/agl.inc

# override what xt-distro wants as machine as we don't depend on HW
MACHINE = "generic-armv8"

XT_BB_IMAGE_TARGET ?= "agl-image-minimal"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

###############################################################################
# meta-xt-images-extra handling
###############################################################################
# N.B. Do not add meta-xt-images-extra layer to bblayers via xt quirks,
# but add it via meta-agl's 50_bblayers.conf.inc by running AGL setup script.
# This is done this way so we can add our generic armv8
# machine into AGL's Yocto at the time when we cannot use bitbake's
# add-layer functionality (xt quirks) because of cross dependencies:
# AGL needs machine's conf file to be inside known layers at the
# time of its setup and also cannot run add-layers because machine is not
# yet known.
# Still we need to copy meta-xt-images-extra into the inner build.

###############################################################################
# extra layers and files to be put after Yocto's do_unpack into inner builder
###############################################################################
# these will be populated into the inner build system on do_unpack_xt_extras
XT_QUIRK_UNPACK_SRC_URI += " \
    file://meta-agl;subdir=repo \
    file://meta-xt-images-extra;subdir=repo \
"

################################################################################
# set to AUTOREV so we can override this while reconstructing this build with
# specific commit ID
################################################################################
SRCREV = "${AUTOREV}"

configure_versions_base() {
    local local_conf="${S}/build/conf/local.conf"

    cd ${S}

    base_update_conf_value ${local_conf} "PREFERRED_PROVIDER_virtual/kernel" "linux-generic-armv8"
}

python do_configure_append() {
    bb.build.exec_func("configure_versions_base", d)
}

