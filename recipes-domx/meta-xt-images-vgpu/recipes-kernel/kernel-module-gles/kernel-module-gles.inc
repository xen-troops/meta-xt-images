DESCRIPTION = "Kernel module of PowerVR GPU"
LICENSE = "GPLv2 & MIT"
LIC_FILES_CHKSUM = " \
    file://GPL-COPYING;md5=60422928ba677faaa13d6ab5f5baaa1e \
    file://MIT-COPYING;md5=8c2810fa6bfdc5ae5c15a0c1ade34054 \
"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

inherit module
PN = "kernel-module-gles"
PR = "r1"
COMPATIBLE_MACHINE = "(r8a7795|r8a7796|r8a77965)"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PVRKM_URL ?= "git://github.com:xen-troops/pvr_km.git"
BRANCH ?= "master"

SRC_URI = "${PVRKM_URL};protocol=ssh;branch=${BRANCH}"

S = "${WORKDIR}/git"
B = "${KBUILD_DIR}"
BUILD ?= "release"

RCAR_TARGET ?= "unknown"
RCAR_TARGET_r8a7795-es2 ?= "r8a7795_es2"
RCAR_TARGET_r8a7795-es3 ?= "r8a7795"
RCAR_TARGET_r8a7796 ?= "r8a7796"
RCAR_TARGET_r8a77965 ?= "r8a77965"

# Build GFX kernel module without suffix
KERNEL_MODULE_PACKAGE_SUFFIX = ""

# Auto load pvrsrvkm
KERNEL_MODULE_AUTOLOAD_append = " pvrsrvkm"

KBUILD_DIR ?= "${S}/build/linux/${RCAR_TARGET}_linux"
KBUILD_OUTDIR ?= "binary_${RCAR_TARGET}_linux_nullws_drm_${BUILD}/target_aarch64/kbuild/"

EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_BUILDDIR}"
EXTRA_OEMAKE += "CROSS_COMPILE=${CROSS_COMPILE}"
EXTRA_OEMAKE += "PVRSRV_VZ_NUM_OSID=${XT_PVR_NUM_OSID}"

module_do_compile() {
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
    cd ${KBUILD_DIR}
    oe_runmake
}

module_do_install() {
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
    install -d ${D}/lib/modules/${KERNEL_VERSION}
    cd ${KBUILD_DIR}
    oe_runmake DISCIMAGE="${D}" install
    install -d ${DEPLOY_DIR_IMAGE}/xt-rcar
    cp -rf ${D}/* ${DEPLOY_DIR_IMAGE}/xt-rcar
}

# Ship the module symbol file to kerenel build dir
SYSROOT_PREPROCESS_FUNCS = "module_sysroot_symbol"

module_sysroot_symbol() {
    install -m 644 ${S}/${KBUILD_OUTDIR}/Module.symvers ${STAGING_KERNEL_BUILDDIR}/GLES.symvers
}

# Clean up the module symbol file
CLEANFUNCS = "module_clean_symbol"

module_clean_symbol() {
    rm -f ${STAGING_KERNEL_BUILDDIR}/GLES.symvers
}

FILES_${PN} = " \
    /lib/modules/${KERNEL_VERSION}/extra/pvrsrvkm.ko \
    ${sysconfdir}/modules-load.d \
"

RPROVIDES_${PN} += "kernel-module-pvrsrvkm kernel-module-dc-linuxfb"
