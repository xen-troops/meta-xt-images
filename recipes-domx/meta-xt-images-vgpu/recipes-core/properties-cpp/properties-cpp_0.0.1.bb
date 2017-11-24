SUMMARY = "A very simple convenience library for handling properties and signals in C++11."
HOMEPAGE = "https://launchpad.net/properties-cpp"

LICENSE = "LGPLV3"
LIC_FILES_CHKSUM = "file://COPYING;md5=e6a600fd5e1d9cbde2d983680233ad02"

SRC_URI = "bzr://bazaar.launchpad.net/~phablet-team/properties-cpp/trunk"
SRCREV = "17"

S = "${WORKDIR}/trunk"

inherit pkgconfig

do_compile () {
    cmake ${S}/data/
}

do_install() {
    # Install header files
    install -d ${D}/${includedir}/core
    install -m 644 ${S}/include/core/*.h ${D}/${includedir}/core/

    # Install pkgconfig file
    install -d ${D}${libdir}/pkgconfig
    install -m 644 ${S}/${PN}.pc ${D}${libdir}/pkgconfig/
}

FILES_${PN} = " \
    ${includedir}/* \
    ${libdir}/pkgconfig/* \
"

