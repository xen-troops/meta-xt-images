FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

COMPILER_URL = "${TOPDIR}/../proprietary/rcar/meta/META_Embedded_Toolkit_2.8.1.0.3.1.zip"

# we already provide the patched rc.pvr file
SRC_URI_remove = " \
    file://change-shell.patch \
"
SRC_URI_append = " \
    file://${COMPILER_URL} \
    file://pvr-addons/etc/powervr.ini \
    file://pvr-addons/lib/pkgconfig/egl.pc \
    file://pvr-addons/lib/pkgconfig/glesv2.pc \
    file://rcpvr-change-shell.patch \
    file://gcc_6_enable_c11.patch \
"

PVRUM_DISCIMAGE = "${D}"
DEPENDS += "virtual/kernel llvmpvr wayland-kms"
BUILD = "release"
PROVIDES += "virtual/opencl libopencl"
RPROVIDES_${PN} += "libopencl"
S = "${WORKDIR}/git"

EXTRA_OEMAKE += "CROSS_COMPILE=${TARGET_PREFIX}"
EXTRA_OEMAKE += "PVR_BUILD_DIR=${PVRUM_BUILD_DIR}"
EXTRA_OEMAKE += "DISCIMAGE=${PVRUM_DISCIMAGE}"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_BUILDDIR}"
EXTRA_OEMAKE += "METAG_INST_ROOT=${S}/metag/2.8"
EXTRA_OEMAKE += "LLVM_BUILD_DIR=${STAGING_LIBDIR}/llvm_build_dir"

# Need to actually delete the varflag not just unset it.
python __anonymous() {
    d.delVarFlag('do_compile', 'noexec')
}

do_configure_prepend() {
    if [ -f ${WORKDIR}/Meta_Embedded_Toolkit-2.8.1.CentOS-5.tar.gz ]
    then
        tar -xzf ${WORKDIR}/Meta_Embedded_Toolkit-2.8.1.CentOS-5.tar.gz
        rm -f ${WORKDIR}/Meta_Embedded_Toolkit-2.8.1.CentOS-5.tar.gz
        rm -f ${WORKDIR}/*.pdf
        ./Meta_Embedded_Toolkit-2.8.1.CentOS-5/install.sh ${S}
        rm -rf ${S}/Meta_Embedded_Toolkit-2.8.1.CentOS-5
    fi
}

# It is not enough just adding dependency on virtual/kernel
# https://stackoverflow.com/questions/34793697/how-to-write-a-bitbake-driver-recipe-which-requires-kernel-source-header-files
do_compile[depends] += "linux-renesas:do_shared_workdir"

do_install() {
    oe_runmake install

    # Install configuration files
    install -m 644 ${WORKDIR}/pvr-addons/${sysconfdir}/powervr.ini ${D}/${sysconfdir}

    # Install pkgconfig
    install -d ${D}/${libdir}/pkgconfig
    install -m 644 ${WORKDIR}/pvr-addons/lib/pkgconfig/*.pc ${D}/${libdir}/pkgconfig/

    if [ "${USE_GLES_WAYLAND}" = "1" ]; then
        # Set the "WindowSystem" parameter for wayland
        if [ "${GLES}" = "gsx" ]; then
            sed -i -e "s/WindowSystem=libpvrDRM_WSEGL.so/WindowSystem=libpvrWAYLAND_WSEGL.so/g" \
                ${D}/${sysconfdir}/powervr.ini
        fi
    fi

    # Install systemd service
    if [ ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)} ]; then
        install -d ${D}/${systemd_system_unitdir}/
        install -m 644 ${WORKDIR}/rc.pvr.service ${D}/${systemd_system_unitdir}/
        install -d ${D}/${exec_prefix}/bin
        install -m 755 ${D}/${sysconfdir}/init.d/rc.pvr ${D}/${exec_prefix}/bin/pvrinit
    fi
}

FILES_${PN} += " \
    ${exec_prefix}/local/share/* \
    ${exec_prefix}/local/bin/* \
"

INSANE_SKIP_${PN} += "dev-so"
