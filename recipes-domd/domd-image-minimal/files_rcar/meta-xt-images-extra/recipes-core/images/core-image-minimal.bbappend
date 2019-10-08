IMAGE_INSTALL_append = " \
    xen-base \
    xen-flask \
    xen-xenstat \
"

# Configuration for ARM Trusted Firmware
EXTRA_IMAGEDEPENDS += " arm-trusted-firmware"

# u-boot
EXTRA_IMAGEDEPENDS += " u-boot"
