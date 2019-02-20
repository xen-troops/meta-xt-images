require gles-module-egl-headers.inc 

SRC_URI_remove = " \
    file://0001-EGL-eglext.h-Include-eglmesaext.h.patch \
    file://GLES-gl3ext.h.patch \
"
