require xen-common.inc
LIC_FILES_CHKSUM = "file://COPYING;md5=419739e325a50f3d7b4501338e44a4e5"

XEN_REL = "4.14"
PV = "${XEN_REL}.0+git${SRCPV}"
SRCREV = "${AUTOREV}"

PACKAGES_append = "\
    ${PN}-libxenhypfs \
    ${PN}-libxenhypfs-dev \
    ${PN}-xenhypfs \
    "

FILES_${PN}-xenhypfs = "\
    ${sbindir}/xenhypfs \
    "

FILES_${PN}-libxenhypfs = "${libdir}/libxenhypfs.so.*"
FILES_${PN}-libxenhypfs-dev = " \
    ${libdir}/libxenhypfs.so \
    ${libdir}/pkgconfig/xenhypfs.pc \
    "

FILES_${PN}-libxentoollog = "${libdir}/libxentoollog.so.*"
FILES_${PN}-libxentoollog-dev = " \
    ${libdir}/libxentoollog.so \
    ${libdir}/pkgconfig/xentoollog.pc \
    "

FILES_${PN}-libxengnttab = "${libdir}/libxengnttab.so.*"
FILES_${PN}-libxengnttab-dev = " \
    ${libdir}/libxengnttab.so \
    ${libdir}/pkgconfig/xengnttab.pc \
    "

FILES_${PN}-libxenguest = "${libdir}/libxenguest.so.*"
FILES_${PN}-libxenguest-dev = " \
    ${libdir}/libxenguest.so \
    ${libdir}/pkgconfig/xenguest.pc \
    "

FILES_${PN}-libxenlight = "${libdir}/libxenlight.so.*"
FILES_${PN}-libxenlight-dev = " \
    ${libdir}/libxenlight.so \
    ${libdir}/pkgconfig/xenlight.pc \
    "

FILES_${PN}-libxenstat = "${libdir}/libxenstat.so.*"
FILES_${PN}-libxenstat-dev = " \
    ${libdir}/libxenstat.so \
    ${libdir}/pkgconfig/xenstat.pc \
    "

FILES_${PN}-libxenstore = "${libdir}/libxenstore.so.*"
FILES_${PN}-libxenstore-dev = " \
    ${libdir}/libxenstore.so \
    ${libdir}/pkgconfig/xenstore.pc \
    "

FILES_${PN}-libxenctrl = "${libdir}/libxenctrl.so.*"
FILES_${PN}-libxenctrl-dev = " \
    ${libdir}/libxenctrl.so \
    ${libdir}/pkgconfig/xencontrol.pc \
    "

FILES_${PN}-libxenvchan = "${libdir}/libxenvchan.so.*"
FILES_${PN}-libxenvchan-dev = " \
    ${libdir}/libxenvchan.so \
    ${libdir}/pkgconfig/xenvchan.pc \
    "

FILES_${PN}-libxlutil = "${libdir}/libxlutil.so.*"
FILES_${PN}-libxlutil-dev = " \
    ${libdir}/libxlutil.so \
    ${libdir}/pkgconfig/xlutil.pc \
    "

FILES_${PN}-libxendevicemodel = "${libdir}/libxendevicemodel.so.*"
FILES_${PN}-libxendevicemodel-dev = " \
    ${libdir}/libxendevicemodel.so \
    ${libdir}/pkgconfig/xendevicemodel.pc \
    "

FILES_${PN}-libxencall = "${libdir}/libxencall.so.*"
FILES_${PN}-libxencall-dev = " \
    ${libdir}/libxencall.so \
    ${libdir}/pkgconfig/xencall.pc \
    "

FILES_${PN}-libxenevtchn = "${libdir}/libxenevtchn.so.*"
FILES_${PN}-libxenevtchn-dev = " \
    ${libdir}/libxenevtchn.so \
    ${libdir}/pkgconfig/xenevtchn.pc \
    "

FILES_${PN}-libxenforeignmemory = "${libdir}/libxenforeignmemory.so.*"
FILES_${PN}-libxenforeignmemory-dev = " \
    ${libdir}/libxenforeignmemory.so \
    ${libdir}/pkgconfig/xenforeignmemory.pc \
    "

FILES_${PN}-libxentoolcore = "${libdir}/libxentoolcore.so.*"
FILES_${PN}-libxentoolcore-dev = " \
	${libdir}/libxentoolcore.so \
	${libdir}/pkgconfig/xentoolcore.pc \
	"

FILES_${PN}-libfsimage = "${libdir}/libxenfsimage.so.*"

FILES_${PN}-libfsimage-dev = " \
    ${libdir}/libxenfsimage.so \
    ${datadir}/pkgconfig/fsimage.pc \
    "

FILES_${PN}-fsimage = "${libdir}/xenfsimage/*/fsimage.so"

FILES_${PN}-misc_append = "\
    ${libdir}/xen/bin/depriv-fd-checker \
    ${bindir}/vchan-socket-proxy \
    "

FILES_${PN}-xenmon = "\
    ${sbindir}/xenbaked \
    ${sbindir}/xentrace_setmask \
    ${sbindir}/xenmon \
    "
