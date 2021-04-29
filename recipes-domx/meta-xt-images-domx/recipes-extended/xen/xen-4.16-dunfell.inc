require xen-common.inc
LIC_FILES_CHKSUM = "file://COPYING;md5=419739e325a50f3d7b4501338e44a4e5"

XEN_REL = "4.16"
PV = "${XEN_REL}.0+git${SRCPV}"
SRCREV = "${AUTOREV}"

PACKAGES_append = "\
    ${PN}-libxenhypfs \
    ${PN}-libxenhypfs-dev \
    ${PN}-xenhypfs \
    ${PN}-xen-access \
    "

FILES_${PN}-xenhypfs = "\
    ${sbindir}/xenhypfs \
    "

FILES_${PN}-libxenhypfs = "${libdir}/libxenhypfs.so.*"
FILES_${PN}-libxenhypfs-dev = " \
    ${libdir}/libxenhypfs.so \
    ${libdir}/pkgconfig/xenhypfs.pc \
    "

FILES_${PN}-libxendevicemodel = "${libdir}/libxendevicemodel.so.*"
FILES_${PN}-libxendevicemodel-dev = " \
    ${libdir}/libxendevicemodel.so \
    ${libdir}/pkgconfig/xendevicemodel.pc \
    "

FILES_${PN}-misc_append = "\
    ${libdir}/xen/bin/depriv-fd-checker \
    ${bindir}/vchan-socket-proxy \
    "

FILES_${PN}-xl += " \
    ${sysconfdir}/bash_completion.d \
    "

FILES_${PN}-xl-examples += " \
    ${sysconfdir}/xen/xlexample.pvhlinux \
    "

FILES_${PN}-xen-access += " \
    ${sbindir}/xen-access \
    "

