RENESAS_BSP_URL = "git://github.com/xen-troops/linux.git"
BRANCH = "vgpu-dev"
SRCREV = "${AUTOREV}"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append = " \
    file://defconfig \
"

SRC_URI_append_salvator-x-domx = " \
    file://r8a7795-salvator-x-dom0.dts;subdir=git/arch/${ARCH}/boot/dts/renesas \
    file://r8a7795-salvator-x-domu.dts;subdir=git/arch/${ARCH}/boot/dts/renesas \
"
SRC_URI_append_h3ulcb = ""
SRC_URI_append_m3ulcb-domx = "file://r8a7796-m3ulcb-dom0.dts;subdir=git/arch/${ARCH}/boot/dts/renesas"

do_install_append() {
    install -d ${D}${base_prefix}/xen
}

do_install_append_salvator-x-domx() {
    install -m 0644 ${B}/arch/${ARCH}/boot/dts/renesas/r8a7795-salvator-x-domu.dtb ${D}${base_prefix}/xen/domu.dtb
}

PACKAGES += "guest-images"
FILES_guest-images = "${base_prefix}/xen/*"
