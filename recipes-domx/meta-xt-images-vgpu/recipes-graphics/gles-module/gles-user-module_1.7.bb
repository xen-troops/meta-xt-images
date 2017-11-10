require gles-common.inc
require gles-user-module.inc

SRC_URI_append = " \
    file://gcc_6_enable_c11.patch \
"

PREFERRED_VERSION_gles-module-egl-headers = "1.7"
