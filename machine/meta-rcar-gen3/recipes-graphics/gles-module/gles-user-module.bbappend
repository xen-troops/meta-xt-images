FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
COMPILER_URL = "${TOPDIR}/../proprietary/rcar/meta/META_Embedded_Toolkit_2.8.1.0.3.1.zip"

SRC_URI_remove = "file://change-shell.patch \
                  file://0001-EGL-eglext.h-Include-eglmesaext.h-to-avoid-compile-error.patch \
"
SRC_URI_append = "file://${COMPILER_URL} \
                  file://pkgconfig/egl.pc \
                  file://pkgconfig/glesv2.pc \
                  file://pvrinit \
                  file://rcpvr-change-shell.patch \
                  file://0001-EGL-eglext.h-Include-eglmesaext.h.patch \
"
DEPENDS += "virtual/kernel wayland-kms libgbm mesa"

EXTRA_OEMAKE += "CROSS_COMPILE=${TARGET_PREFIX}"
EXTRA_OEMAKE += "PVR_BUILD_DIR=${PVRUM_BUILD_DIR}"
EXTRA_OEMAKE += "DISCIMAGE=${PVRUM_DISCIMAGE}"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_BUILDDIR}"
EXTRA_OEMAKE += "METAG_INST_ROOT=${S}/metag/2.8"

# Need to actually delete the varflag not just unset it.
python __anonymous() {
    d.delVarFlag('do_compile', 'noexec')
}

# Since we depend on wayland-kms we have to provide
# following header files to make it buildable.
do_populate_headers() {
    # Install header files
    install -d ${STAGING_DIR_TARGET}/${includedir}/EGL
    install -m 644 ${S}/include/khronos/EGL/*.h ${STAGING_DIR_TARGET}/${includedir}/EGL/
    install -d ${STAGING_DIR_TARGET}/${includedir}/GLES2
    install -m 644 ${S}/include/khronos/GLES2/*.h ${STAGING_DIR_TARGET}/${includedir}/GLES2/
    install -d ${STAGING_DIR_TARGET}/${includedir}/GLES3
    install -m 644 ${S}/include/khronos/GLES3/*.h ${STAGING_DIR_TARGET}/${includedir}/GLES3/
    install -d ${STAGING_DIR_TARGET}/${includedir}/KHR
    install -m 644 ${S}/include/khronos/KHR/khrplatform.h ${STAGING_DIR_TARGET}/${includedir}/KHR/khrplatform.h
    install -m 644 ${S}/include/khronos/drv/EGL/eglext_REL.h ${STAGING_DIR_TARGET}/${includedir}/EGL/
}

do_patch_append() {
    bb.build.exec_func("do_populate_headers", d)
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

do_install() {
    oe_runmake install

    # Install systemd service
    if [ ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)} ]; then
        install -d ${D}/${systemd_system_unitdir}/
        install -m 644 ${WORKDIR}/rc.pvr.service ${D}/${systemd_system_unitdir}/
    fi

    # Install pkgconfig
    install -d ${D}/${libdir}/pkgconfig
    install -m 644 ${WORKDIR}/pkgconfig/*.pc ${D}/${libdir}/pkgconfig/

    # Install init script
    install -d ${D}/${bindir}
    install -m 755 ${WORKDIR}/pvrinit ${D}/${bindir}/pvrinit
}

FILES_${PN} += "${exec_prefix}/local/share/*"
INSANE_SKIP_${PN} += "dev-so"
