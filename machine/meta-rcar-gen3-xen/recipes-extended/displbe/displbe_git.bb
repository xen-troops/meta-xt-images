DESCRIPTION = "displbe" 
SECTION = "extras" 
LICENSE = "GPLv2" 
PR = "r0" 

DEPENDS = "libxenbe libconfig"

LIC_FILES_CHKSUM = "file://LICENSE;md5=a23a74b3f4caf9616230789d94217acb"

SRC_URI = "git://github.com/xen-troops/displ_be.git;protocol=https;branch=vgpu-dev"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

EXTRA_OECMAKE="-DWITH_DOC=OFF -DWITH_DRM=ON -DWITH_ZCOPY=OFF -DWITH_WAYLAND=ON -DWITH_IVI_EXTENSION=OFF -DWITH_INPUT=ON"

inherit pkgconfig cmake

