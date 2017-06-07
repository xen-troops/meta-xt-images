RENESAS_BSP_URL = "git://github.com/xen-troops/linux.git"
BRANCH = "vgpu-dev"
SRCREV = "${AUTOREV}"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append = " \
    file://defconfig \
"
