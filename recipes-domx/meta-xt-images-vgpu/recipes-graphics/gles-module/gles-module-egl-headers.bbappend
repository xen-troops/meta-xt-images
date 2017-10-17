S = "${WORKDIR}/git"

do_install() {
    # Install header files
    install -d ${STAGING_DIR_TARGET}/${includedir}/CL
    install -m 644 ${S}/include/khronos/CL/*.h ${STAGING_DIR_TARGET}/${includedir}/CL/
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

