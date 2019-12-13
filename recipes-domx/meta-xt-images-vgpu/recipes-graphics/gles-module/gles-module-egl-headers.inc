require gles-common.inc

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append = " \
    file://GLES-gl3ext.h.patch \
"

DESCRIPTION = "PowerVR GPU user EGL header files"
LICENSE = "CLOSED"

PN = "gles-module-egl-headers"
PR = "r1"

do_compile[noexec] = "1"

# The recipe needs headers from virtual/mesa.
do_populate_sysroot[depends] += "virtual/mesa:do_populate_sysroot"

do_configure() {
    :
}

do_install() {
    # Install header files
    if [ -d ${S}/include/khronos/CL ]; then
        install -d ${D}/${includedir}/CL
        install -m 644 ${S}/include/khronos/CL/*.h ${D}/${includedir}/CL/
    fi
    install -d ${D}/${includedir}/EGL
    install -m 644 ${S}/include/khronos/EGL/*.h ${D}/${includedir}/EGL/
    install -d ${D}/${includedir}/GLES2
    install -m 644 ${S}/include/khronos/GLES2/*.h ${D}/${includedir}/GLES2/
    install -d ${D}/${includedir}/GLES3
    install -m 644 ${S}/include/khronos/GLES3/*.h ${D}/${includedir}/GLES3/
    install -d ${D}/${includedir}/KHR
    install -m 644 ${S}/include/khronos/KHR/khrplatform.h ${D}/${includedir}/KHR/khrplatform.h
    install -m 644 ${S}/include/khronos/drv/EGL/eglext_REL.h ${D}/${includedir}/EGL/
    install -d ${DEPLOY_DIR_IMAGE}/xt-rcar
    cp -rf ${D}/* ${DEPLOY_DIR_IMAGE}/xt-rcar
}

RDEPENDS_${PN}-dev = ""
