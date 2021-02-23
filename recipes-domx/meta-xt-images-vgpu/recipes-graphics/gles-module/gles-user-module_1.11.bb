require gles-common.inc
require gles-user-module.inc

PREFERRED_VERSION_gles-module-egl-headers = "1.11"

DEPENDS += " \
    clang-native \
    python-native \
    python-clang-native \
    wayland-protocols \
"
DEPENDS_remove = " \
    binutils-cross-aarch64 \
    llvmpvr \
"

PROVIDES_remove = "virtual/opencl libopencl"
RPROVIDES_${PN}_remove = " \
    libopencl \
"
EXTRA_OEMAKE += "LIBCLANG_PATH=${STAGING_LIBDIR_NATIVE}/libclang.so"
EXTRA_OEMAKE_remove = "LLVM_BUILD_DIR=${STAGING_LIBDIR}/llvm_build_dir"

EXCLUDED_APIS = ""
EXTRA_OEMAKE += "EXCLUDED_APIS='${EXCLUDED_APIS}'"
RDEPENDS_${PN} += "python3-core"
