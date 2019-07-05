require xen-4.10-rocko.inc

XEN_REL = "4.12"
PV = "${XEN_REL}.0+git${SRCPV}"
SRCREV = "${AUTOREV}"

FILES_${PN}-libfsimage = "${libdir}/libxenfsimage.so.*"

FILES_${PN}-libfsimage-dev = " \
    ${libdir}/libxenfsimage.so \
    ${datadir}/pkgconfig/fsimage.pc \
    "

FILES_${PN}-fsimage = "${libdir}/xenfsimage/*/fsimage.so"

FILES_${PN}-misc_append = "\
    ${libdir}/xen/bin/depriv-fd-checker \
    "

FILES_${PN}-xenmon = "\
    ${sbindir}/xenbaked \
    ${sbindir}/xentrace_setmask \
    ${sbindir}/xenmon \
    "
