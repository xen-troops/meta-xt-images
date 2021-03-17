DEPENDS += "u-boot-mkimage-native systemd"

PACKAGECONFIG ?= " \
    xsm \
    ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'systemd', '', d)} \
    ${@bb.utils.contains('XEN_TARGET_ARCH', 'x86_64', 'hvm', '', d)} \
    "

EXTRA_OEMAKE += " CONFIG_HAS_SCIF=y CONFIG_QEMU_XEN=n"

