SUMMARY = "Wayland IVI Extension"
DESCRIPTION = "GENIVI Layer Management API based on Wayland IVI Extension"
HOMEPAGE = "http://projects.genivi.org/wayland-ivi-extension"
BUGTRACKER = "http://bugs.genivi.org/enter_bug.cgi?product=Wayland%20IVI%20Extension"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1f1a56bb2dadf5f2be8eb342acf4ed79"

PR = "r1"
SRCREV = "e232017e0906557f468823505a49e92d4c94591c"
SRC_URI = "git://github.com/GENIVI/${BPN}.git;protocol=https \
    "
S = "${WORKDIR}/git"

DEPENDS = "weston"

inherit cmake

EXTRA_OECMAKE := "-DWITH_ILM_INPUT=1"

FILES_${PN} += "${libdir}/weston/*"
FILES_${PN}-dbg += "${libdir}/weston/.debug/*"

EXTRA_OECMAKE += "-DLIB_SUFFIX=${@d.getVar('baselib', True).replace('lib', '')}"
