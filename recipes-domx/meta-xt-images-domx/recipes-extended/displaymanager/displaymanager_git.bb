DESCRIPTION = "displaymanager"
SECTION = "extras"
LICENSE = "GPLv2"
PR = "r0"

DEPENDS = "libxenbe libconfig wayland-ivi-extension dbus-cpp"

LIC_FILES_CHKSUM = "file://LICENSE;md5=a23a74b3f4caf9616230789d94217acb"

S = "${WORKDIR}/git"

inherit pkgconfig cmake