DESCRIPTION = "camerabe"
SECTION = "extras"
LICENSE = "GPLv2"
PR = "r0"

DEPENDS = "libxenbe libconfig libv4l git-native"

LIC_FILES_CHKSUM = "file://LICENSE;md5=b234ee4d69f5fce4486a80fdaf4a4263"

S = "${WORKDIR}/git"

inherit pkgconfig cmake
