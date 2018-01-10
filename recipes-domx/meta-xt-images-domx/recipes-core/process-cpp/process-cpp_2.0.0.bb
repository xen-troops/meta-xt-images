SUMMARY = "A simple convenience library for handling processes in C++11."
HOMEPAGE = "https://launchpad.net/process-cpp"

LICENSE = "LGPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=e6a600fd5e1d9cbde2d983680233ad02"
DEPENDS = "boost \
           bzr-native \
           properties-cpp \
"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI = "bzr://bazaar.launchpad.net/~phablet-team/process-cpp/trunk \
           file://remove_unused.patch \
"
SRCREV = "54"

S = "${WORKDIR}/trunk"

inherit pkgconfig cmake

do_compile_prepend () {
     if [ -d ${S}/build ]; then
         cd ${S}/build
         cmake ..
     fi
}
