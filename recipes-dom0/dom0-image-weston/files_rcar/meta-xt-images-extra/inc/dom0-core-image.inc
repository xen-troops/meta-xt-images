#Add Xen to build
IMAGE_INSTALL_append = " \
    xen-base \
    xen-flask \
    guests \
"

populate_append() {
	install -m 0644 ${DEPLOY_DIR_IMAGE}/xen-${MACHINE}.gz ${DEST}/xen.gz
}

IMAGE_INSTALL_append = " \
    guest-images \
"

# Configuration for ARM Trusted Firmware
EXTRA_IMAGEDEPENDS += " arm-trusted-firmware"

# u-boot
PREFERRED_VERSION_u-boot = "v2015.04%"
EXTRA_IMAGEDEPENDS += " u-boot"
