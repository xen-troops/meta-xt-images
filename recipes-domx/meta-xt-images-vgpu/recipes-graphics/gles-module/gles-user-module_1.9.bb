require gles-common.inc
require gles-user-module.inc

SRC_URI_append = " \
    file://gcc6_pvr_um_1.9.patch \
"

PREFERRED_VERSION_gles-module-egl-headers = "1.9"

DEPENDS += " \
    clang-native \
    wayland-protocols \
"

EXTRA_OEMAKE += "LIBCLANG_PATH=${STAGING_LIBDIR_NATIVE}/libclang.so"
