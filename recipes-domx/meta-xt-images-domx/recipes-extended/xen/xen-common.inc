DEPENDS += "u-boot-mkimage-native systemd"

PACKAGECONFIG ?= " \
    xsm \
    ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'systemd', '', d)} \
    ${@bb.utils.contains('XEN_TARGET_ARCH', 'x86_64', 'hvm', '', d)} \
    "

EXTRA_OEMAKE += " CONFIG_HAS_SCIF=y CONFIG_QEMU_XEN=n"

RDEPENDS_${PN}-base += "\
    ${PN}-livepatch \
    "

RDEPENDS_${PN}-base_remove += " \
    ${PN}-blktap \
    ${PN}-libblktapctl \
    ${PN}-libvhd \
    "

RRECOMMENDS_${PN}-base += " \
    ${PN}-blktap \
    ${PN}-libblktapctl \
    ${PN}-libvhd \
    "

FILES_${PN}-hypervisor += "\
    /usr/lib/debug/xen-syms-* \
    "

FILES_${PN}-scripts-block += " \
    ${sysconfdir}/xen/scripts/block-dummy \
    "

FILES_${PN}-scripts-network += " \
    ${sysconfdir}/xen/scripts/colo-proxy-setup \
    "

FILES_${PN}-staticdev += "\
    ${exec_prefix}/lib64/libxenstore.a \
    ${exec_prefix}/lib64/libxenvchan.a \
    "

FILES_${PN}-xencommons += " \
    ${systemd_unitdir}/system/xendriverdomain.service \
    ${sysconfdir}/xen/scripts/launch-xenstore \
    "

FILES_${PN}-xencommons_remove += " \
    ${systemd_unitdir}/system/xenstored.socket \
    ${systemd_unitdir}/system/xenstored_ro.socket \
    "

FILES_${PN}-libxendevicemodel = "${libdir}/libxendevicemodel.so.*"
FILES_${PN}-libxendevicemodel-dev = "${libdir}/libxendevicemodel.so"

FILES_${PN}-pkgconfig = "\
    ${datadir}/pkgconfig \
    "

PACKAGES_append = "\
    ${PN}-pkgconfig \
"

SYSTEMD_SERVICE_${PN}-xencommons += " \
    xendriverdomain.service \
    "

SYSTEMD_SERVICE_${PN}-xencommons_remove += " \
    xenstored.socket \
    xenstored_ro.socket \
    "

RDEPENDS_${PN}-efi = " \
    bash \
    "

FILES_${PN}-misc_append = "\
    ${sbindir}/xen-diag \
    "
