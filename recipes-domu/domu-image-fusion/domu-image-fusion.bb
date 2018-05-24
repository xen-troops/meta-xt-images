require inc/fusion.inc

# override what xt-distro wants as machine as we don't depend on HW
MACHINE = "generic-armv8"

XT_BB_IMAGE_TARGET ?= "core-image-minimal"

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
