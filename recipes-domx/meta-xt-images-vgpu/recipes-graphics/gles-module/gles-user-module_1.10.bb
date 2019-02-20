require gles-common.inc
require gles-user-module.inc

PREFERRED_VERSION_gles-module-egl-headers = "1.10"

DEPENDS += " \
    clang-native \
    wayland-protocols \
"
DEPENDS_remove = " \
    llvmpvr \
"

PROVIDES_remove = "virtual/opencl libopencl"
RPROVIDES_${PN}_remove = " \
    libopencl \
"
EXTRA_OEMAKE_remove = "LLVM_BUILD_DIR=${STAGING_LIBDIR}/llvm_build_dir"
EXTRA_OEMAKE += "LIBCLANG_PATH=${STAGING_LIBDIR_NATIVE}/libclang.so"
