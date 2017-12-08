SUMMARY = "A header-only dbus-binding leveraging C++-11."
HOMEPAGE = "https://launchpad.net/dbus-cpp"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
LICENSE = "GPLv2 | LGPLv3"
LIC_FILES_CHKSUM = "file://COPYING.GPL;md5=b234ee4d69f5fce4486a80fdaf4a4263"
DEPENDS = "dbus \
           boost \
           libxml2 \
           properties-cpp \
           process-cpp \
"
SRC_URI = "bzr://bazaar.launchpad.net/~phablet-team/dbus-cpp/trunk \
           file://set_distro.patch \
"
SRCREV = "105"

S = "${WORKDIR}/trunk"

inherit pkgconfig cmake

do_configure () {
    :
}

do_compile_prepend () {
     if [ -d ${WORKDIR}/build ]; then
         cd ${WORKDIR}/build
         cmake -DCMAKE_INSTALL_PREFIX=/usr \
               -DCMAKE_TOOLCHAIN_FILE=${WORKDIR}/toolchain.cmake ${S}
     fi
}

FILES_${PN} += " \
    ${bindir} \
    ${libdir} \
    ${includedir} \
    ${datadir} \
"

FILES_${PN}-dev += " \
    /usr/libexec/* \
"

INSANE_SKIP_${PN} += "dev-so libdir"
