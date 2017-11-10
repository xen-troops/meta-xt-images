FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

DESCRIPTION = "PowerVR GPU user module"
LICENSE = "CLOSED"

DEPENDS = " \
    gles-module-egl-headers \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'libgbm wayland-kms', '', d)} \
    virtual/kernel \
    wayland-kms \
    llvmpvr \
"

PN = "gles-user-module"
PR = "r1"
GLES = "gsx"

SRC_URI_append = " \
    file://${COMPILER_URL} \
    file://pvr-addons/etc/powervr.ini \
    file://pvr-addons/lib/pkgconfig/egl.pc \
    file://pvr-addons/lib/pkgconfig/glesv2.pc \
    file://rcpvr-change-shell.patch \
    file://rc.pvr.service \
"

inherit update-rc.d systemd

INITSCRIPT_NAME = "pvrinit"
INITSCRIPT_PARAMS = "start 7 5 2 . stop 62 0 1 6 ."
SYSTEMD_SERVICE_${PN} = "rc.pvr.service"

PROVIDES = "virtual/libgles2 virtual/egl virtual/opencl libopencl"
RPROVIDES_${PN} += " \
    ${GLES}-user-module \
    libgles2-mesa \
    libgles2-mesa-dev \
    libgles2 \
    libgles2-dev \
    libegl \
    libegl1 \
    libopencl \
"

EXTRA_OEMAKE += "CROSS_COMPILE=${TARGET_PREFIX}"
EXTRA_OEMAKE += "PVR_BUILD_DIR=${PVRUM_BUILD_DIR}"
EXTRA_OEMAKE += "DISCIMAGE=${PVRUM_DISCIMAGE}"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_BUILDDIR}"
EXTRA_OEMAKE += "METAG_INST_ROOT=${S}/metag/2.8"
EXTRA_OEMAKE += "LLVM_BUILD_DIR=${STAGING_LIBDIR}/llvm_build_dir"

RDEPENDS_${PN} = " \
    kernel-module-gles \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'libgbm wayland-kms', '', d)} \
"

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

PACKAGES = "\
    ${PN} \
    ${PN}-dev \
    ${PN}-debug \
"

FILES_${PN} = " \
    ${sysconfdir}/* \
    ${libdir}/* \
    /lib/firmware/rgx.fw* \
    ${localedir}/bin/* \
    ${exec_prefix}/bin/* \
    ${exec_prefix}/local/share/* \
    ${exec_prefix}/local/bin/* \
"

FILES_${PN}-dev = " \
    ${includedir}/* \
    ${libdir}/pkgconfig/* \
"

FILES_${PN}-debug = " \
    ${exec_prefix}/local/bin/dlcsrv_REL \
"

# Skip debug strip of do_populate_sysroot()
INHIBIT_SYSROOT_STRIP = "1"

# Skip debug split and strip of do_package()
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_PACKAGE_STRIP = "1"

INSANE_SKIP_${PN} += "ldflags split-strip arch"

