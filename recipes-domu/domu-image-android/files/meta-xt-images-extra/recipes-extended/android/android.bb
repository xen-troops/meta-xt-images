SUMMARY = "Android"
DESCRIPTION = "Recipe for building Android image"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

S = "${WORKDIR}/repo"

SRCREV = "${AUTOREV}"

require inc/xt_shared_env.inc

inherit java
inherit pythonnative

JDK_VER = "openjdk-8-native"

# remove all depends normally required for oe_make, e.g. cross-compiler
# and tools: these are not needed for Android
BASEDEPENDS = ""

DEPENDS += "${JDK_VER} lz4-native bc-native python-pycrypto-native curl-native rsync-native bison-native"

ANDROID_PRODUCT ?= "aosp_arm64"
ANDROID_VARIANT ?= "eng"
SOC_FAMILY ?= "default"

ANDROID_CCACHE_BIN_DIR = "prebuilts/misc/linux-x86/ccache"
ANDROID_CCACHE_SIZE_GB = "50"
# keep .ccache aside of Yocto's cache
ANDROID_CCACHE_DIR = "${SSTATE_DIR}/../${PN}-${ANDROID_PRODUCT}-${ANDROID_VARIANT}-${SOC_FAMILY}-ccache"

JAVA_HOME = "${STAGING_LIBDIR_JVM_NATIVE}/${JDK_VER}"

ANDROID_OUT_DIR_COMMON_BASE = ""
BOARD_USES_DRM_HWCOMPOSER = "true"

do_configure() {
}

do_compile() {
    cd ${S}

    # run Android build in sane environment
    env -i HOME="$HOME" LC_CTYPE="${LC_ALL:-${LC_CTYPE:-$LANG}}" USER="$USER" \
           PATH="${JAVA_HOME}/bin:${S}/${ANDROID_CCACHE_BIN_DIR}:$PATH" \
           JAVA_HOME="${JAVA_HOME}" OUT_DIR_COMMON_BASE="${ANDROID_OUT_DIR_COMMON_BASE}" \
           USE_CCACHE="1" CCACHE_DIR="${ANDROID_CCACHE_DIR}" CCACHE_BASEDIR="${S}" \
           BOARD_USES_DRM_HWCOMPOSER="${BOARD_USES_DRM_HWCOMPOSER}" \
           bash -c "${ANDROID_CCACHE_BIN_DIR}/ccache -M ${ANDROID_CCACHE_SIZE_GB}G && \
                    source build/envsetup.sh && \
                    lunch ${ANDROID_PRODUCT}-${ANDROID_VARIANT} && \
                    make ${EXTRA_OEMAKE} ${PARALLEL_MAKE} \
           "
}

