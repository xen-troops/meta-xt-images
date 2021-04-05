DESCRIPTION = "OP-TEE OS"

LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=c1f21c4f72f372ef38a5a4aee55ec173"

inherit deploy python3native
DEPENDS = "python3-pycryptodome-native python3-pyelftools-native"
S = "${WORKDIR}/git"

SRC_URI = "git://github.com/xen-troops/optee_os.git;branch=3.9-xt-linux"
PV = "git${SRCPV}"
SRCREV = "${AUTOREV}"

COMPATIBLE_MACHINE ?= "(salvator-x|h3ulcb|m3ulcb|m3nulcb)"
OPTEEMACHINE = "rcar"
PLATFORM = "rcar"

OPTEEFLAVOR_salvator-xs-m3n-xt = "salvator_m3n"

OPTEEFLAVOR_m3ulcb-xt = "salvator_m3"
OPTEEFLAVOR_salvator-x-m3-xt = "salvator_m3"

OPTEEFLAVOR_h3ulcb-xt = "salvator_h3"
OPTEEFLAVOR_h3ulcb-cb-xt = "salvator_h3"

OPTEEFLAVOR_salvator-x-h3-xt = "salvator_h3"
OPTEEFLAVOR_salvator-xs-h3-xt = "salvator_h3"

OPTEEFLAVOR_salvator-x-h3-4x2g-xt = "salvator_h3_4x2g"
OPTEEFLAVOR_salvator-xs-h3-4x2g-xt = "salvator_h3_4x2g"
OPTEEFLAVOR_h3ulcb-4x2g-xt = "salvator_h3_4x2g"
OPTEEFLAVOR_h3ulcb-4x2g-kf-xt = "salvator_h3_4x2g"
OPTEEFLAVOR_salvator-xs-m3-2x4g-xt = "salvator_m3_2x4g"

PACKAGE_ARCH = "${MACHINE_ARCH}"
export CROSS_COMPILE64="${TARGET_PREFIX}"

# Let the Makefile handle setting up the flags as it is a standalone application
LD[unexport] = "1"
LDFLAGS[unexport] = "1"
export CCcore="${CC}"
export LDcore="${LD}"
libdir[unexport] = "1"

EXTRA_OEMAKE = "-e MAKEFLAGS= \
		   PLATFORM=rcar \
	       PLATFORM_FLAVOR=${OPTEEFLAVOR} \
	       CFG_ARM64_core=y \
	       CFG_VIRTUALIZATION=y \
	       CROSS_COMPILE_core=${HOST_PREFIX} \
	       CROSS_COMPILE_ta_arm64=${HOST_PREFIX} \
	       ta-targets=ta_arm64 \
	       CFLAGS64=--sysroot=${STAGING_DIR_HOST} \
	       CFG_SYSTEM_PTA=y \
	       CFG_ASN1_PARSER=y \
	       CFG_CORE_MBEDTLS_MPI=y \
	       "

do_configure() {
}

do_compile() {
	oe_runmake
}

OPTEE_ARCH_aarch64 = "arm64"

do_install () {
    install -d ${D}/usr/include/optee/export-user_ta_${OPTEE_ARCH}/

    for f in ${S}/out/arm-plat-rcar/export-ta_${OPTEE_ARCH}/*; do
        cp -aR $f ${D}/usr/include/optee/export-user_ta_${OPTEE_ARCH}/
    done
}

do_deploy() {
    # Create deploy folder
    install -d ${DEPLOYDIR}

    # Copy TEE OS to deploy folder
    install -m 0644 ${S}/out/arm-plat-${PLATFORM}/core/tee.elf ${DEPLOYDIR}/tee-${MACHINE}.elf
    install -m 0644 ${S}/out/arm-plat-${PLATFORM}/core/tee-raw.bin ${DEPLOYDIR}/tee-${MACHINE}.bin
    install -m 0644 ${S}/out/arm-plat-${PLATFORM}/core/tee.srec ${DEPLOYDIR}/tee-${MACHINE}.srec

    install -m 0644 ${S}/out/arm-plat-${PLATFORM}/core/tee-raw.bin ${DEPLOYDIR}/tee_raw-${MACHINE}.bin
}
addtask deploy before do_build after do_compile

FILES_${PN} = ""
FILES_${PN}-staticdev = "/usr/include/optee/"
