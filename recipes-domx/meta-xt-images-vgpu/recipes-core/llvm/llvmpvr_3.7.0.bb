DESCRIPTION = "The Low Level Virtual Machine"
HOMEPAGE = "http://llvm.org"

# 3-clause BSD-like
# University of Illinois/NCSA Open Source License
LICENSE = "NCSA"
LIC_FILES_CHKSUM = "file://LICENSE.TXT;md5=4c0bc17c954e99fd547528d938832bfa"

inherit autotools
FILESEXTRAPATHS_prepend := "${THISDIR}/llvm:"
RDEPENDS_${PN} += "ncurses ncurses-libtinfo libgcc libstdc++ glibc"

SRC_URI = "http://llvm.org/releases/${PV}/llvm-${PV}.src.tar.xz;name=llvm \
           http://llvm.org/releases/${PV}/cfe-${PV}.src.tar.xz;name=cfe \
"

SRC_URI += "file://llvm37.patch \
            file://llvm37.linux.patch \
            file://clang37.patch \
            file://clang37.linux.patch \
"

SRC_URI[cfe.md5sum] = "8f9d27335e7331cf0a4711e952f21f01"
SRC_URI[cfe.sha256sum] = "4ed740c5a91df1c90a4118c5154851d6a475f39a91346bdf268c1c29c13aa1cc"

SRC_URI[llvm.md5sum] = "b98b9495e5655a672d6cb83e1a180f8e"
SRC_URI[llvm.sha256sum] = "ab45895f9dcdad1e140a3a79fd709f64b05ad7364e308c0e582c5b02e9cc3153"

S = "${WORKDIR}/llvm-${PV}.src"
LLVM_BUILD_DIR = "${WORKDIR}/llvm.${TARGET_ARCH}"

EXTRA_OECONF = "--enable-shared=no \
                --enable-jit=no \
                --enable-bindings=none \
                --enable-targets=none \
                --disable-clang-static-analyzer \
                --disable-clang-arcmt \
                --disable-clang-rewriter \
                --disable-clang-plugin-support \
                --disable-terminfo \
                --disable-libedit \
                --disable-zlib \
                --enable-optimized \
                --enable-assertions \
                ${CONFIGURE_FLAGS}"

EXTRA_OEMAKE += "CROSS_COMPILE=${TARGET_PREFIX}"

move_cfe() {
    if [ -d ${WORKDIR}/cfe-${PV}.src ]; then
       mv ${WORKDIR}/cfe-${PV}.src ${S}/tools/clang
    fi
}

python do_unpack_append() {
    bb.build.exec_func("move_cfe", d)
}

do_configure_prepend() {
    # Fails to build unless using separate directory from source
    mkdir -p ${LLVM_BUILD_DIR}
    cd ${LLVM_BUILD_DIR}
}

do_configure_append() {
    LLVM_BUILD_ARCH=$(${BUILD_CC} -v 2>&1 | awk '/^Target:/{print $2}')
    if [ ! -f BuildTools/Makefile ]; then
         mkdir -p BuildTools
         cd BuildTools
         unset CFLAGS
         unset CXXFLAGS
         AR=${BUILD_AR}
         AS=${BUILD_AS}
         LD=${BUILD_LD}
         CC=${BUILD_CC}
         CXX=${BUILD_CXX}
         unset SDKROOT
         unset UNIVERSAL_SDK_PATH
         ${S}/configure --build=${LLVM_BUILD_ARCH} \
             --host=${LLVM_BUILD_ARCH} --target=${LLVM_BUILD_ARCH} \
             --disable-polly --disable-zlib --enable-libcpp --disable-bindings
         cd ..
    fi

}

do_compile() {
    cd ${LLVM_BUILD_DIR}
    if [ -d ${WORKDIR}/llvm-${PV}.src ];then
        cp ${S}/tools/clang/Makefile ${LLVM_BUILD_DIR}/tools/clang/Makefile
    fi
    oe_runmake llvm-and-clang-libs

    # Add signature files needed for LLVM build system.
    BUILD_SIGNATURE="c069d28b0040069e2e0538c3f9ef055f"
    COMPILER_SIGNATURE=$(${TARGET_PREFIX}gcc -v 2>&1 | awk '/^Target:/{print $2};/^gcc version/{print $3}')
    echo "${BUILD_SIGNATURE}" > ${LLVM_BUILD_DIR}/.signature
    echo "${BUILD_SIGNATURE}" > ${S}/.signature
    echo "${COMPILER_SIGNATURE}" > ${LLVM_BUILD_DIR}/.compiler_signature
}

do_install() {
    if [ -d ${WORKDIR}/llvm-${PV}.src ];then
        install -d ${D}${libdir}/llvm_build_dir
        cp -rf ${S} ${D}${libdir}/llvm_build_dir
        mv ${D}${libdir}/llvm_build_dir/llvm-${PV}.src ${D}${libdir}/llvm_build_dir/llvm.src
        cp -rf ${LLVM_BUILD_DIR}  ${D}${libdir}/llvm_build_dir
    fi
}

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INSANE_SKIP_${PN} += "ldflags split-strip arch staticdev file-rdeps"
FILES_${PN} += "${libdir}/llvm_build_dir/"
