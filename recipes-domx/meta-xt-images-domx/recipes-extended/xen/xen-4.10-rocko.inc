require xen-common.inc
LIC_FILES_CHKSUM = "file://COPYING;md5=bbb4b1bdc2c3b6743da3c39d03249095"

XEN_REL = "4.10"
PV = "${XEN_REL}.0+git${SRCPV}"
SRCREV = "${AUTOREV}"

FILES_${PN}-libxentoolcore = "${libdir}/libxentoolcore.so.*"
FILES_${PN}-libxentoolcore-dev = " \
    ${libdir}/libxentoolcore.so \
    ${datadir}/pkgconfig/xentoolcore.pc \
    "

PACKAGES_append = "\
    ${PN}-pkgconfig \
    ${PN}-libxentoolcore \
    ${PN}-libxentoolcore-dev \
    "
