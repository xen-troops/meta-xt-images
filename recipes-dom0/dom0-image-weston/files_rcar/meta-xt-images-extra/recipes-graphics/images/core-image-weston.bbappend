#Add Xen and additional packages to build
IMAGE_INSTALL_append = " \
    xen-tools \
    pulseaudio \
"

# Configuration for ARM Trusted Firmware
EXTRA_IMAGEDEPENDS += " arm-trusted-firmware"

# u-boot
EXTRA_IMAGEDEPENDS += " u-boot"
