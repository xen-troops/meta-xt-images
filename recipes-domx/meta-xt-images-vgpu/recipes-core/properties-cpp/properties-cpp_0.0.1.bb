SUMMARY = "A very simple convenience library for handling properties and signals in C++11."
HOMEPAGE = "https://launchpad.net/properties-cpp"

LICENSE = "LGPLV3"
LIC_FILES_CHKSUM = "file://COPYING;md5=e6a600fd5e1d9cbde2d983680233ad02"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI = "bzr://bazaar.launchpad.net/~phablet-team/properties-cpp/trunk \
           file://remove_unused.patch \
"

SRCREV = "17"

S = "${WORKDIR}/trunk"
DEPENDS = "bzr-native"

inherit pkgconfig cmake

do_compile_prepend () {
     if [ -d ${S}/build ]; then
         cd ${S}/build
         cmake ..
     fi
}

FILES_${PN} = " \
    ${includedir}/* \
    ${libdir}/pkgconfig/* \
"
ALLOW_EMPTY_${PN} = "1"
