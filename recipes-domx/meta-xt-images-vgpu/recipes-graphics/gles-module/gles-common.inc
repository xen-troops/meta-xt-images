FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

USE_GLES = "${@'1' if 'gsx' in '${MACHINE_FEATURES}' else '0'}"
USE_GLES_WAYLAND = \
    "${@'1' if '${USE_GLES}' == '1' and 'wayland' in '${DISTRO_FEATURES}' else '0'}"
USE_WAYLAND_IVI_SHELL = \
    "${@'1' if 'ivi-shell' in '${DISTRO_FEATURES}' and \
    'wayland' in '${DISTRO_FEATURES}' else '0'}"

COMPATIBLE_MACHINE = "(r8a7795|r8a7796|r8a77965)"
PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPILER_URL = "${TOPDIR}/../proprietary/rcar/meta/META_Embedded_Toolkit_2.8.1.0.3.1.zip"
PVRUM_DISCIMAGE = "${D}"
BUILD = "release"
S = "${WORKDIR}/git"

PVRUM_URL ?= "git://git@gitpct.epam.com/epmd-aepr/pvr_um_vgpu_img.git"
WSEGL_URL ?= "git://git@gitpct.epam.com/epmd-aepr/wayland-wsegl.git"
SRCREV_FORMAT ?= "1"

PVRUM_WITH_WSEGL_URL = "${PVRUM_URL};protocol=ssh;branch=${BRANCH};name=pvr_um_vgpu_img \
                        ${WSEGL_URL};protocol=ssh;destsuffix=${S}/wsegl/wayland-wsegl;branch=${BRANCH};name=wayland-wsegl \
"

SRC_URI_r8a7795 = "${PVRUM_WITH_WSEGL_URL}"
SRC_URI_r8a7796 = "${PVRUM_WITH_WSEGL_URL}"
SRC_URI_r8a77965 = "${PVRUM_WITH_WSEGL_URL}"

PVRUM_BUILD_DIR_r8a7795 = "r8a7795_linux"
PVRUM_BUILD_DIR_r8a7796 = "r8a7796_linux"
PVRUM_BUILD_DIR_r8a77965 = "r8a77965_linux"

do_populate_lic[noexec] = "1"

INSANE_SKIP_${PN} += "dev-so"
