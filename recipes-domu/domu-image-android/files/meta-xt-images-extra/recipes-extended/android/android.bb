SUMMARY = "Android"
DESCRIPTION = "Recipe for building Android image"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

S = "${WORKDIR}/repo"

SRCREV = "${AUTOREV}"

require inc/xt_shared_env.inc

inherit java
inherit pythonnative
inherit xt_reconstruct

JDK_VER = "openjdk-8-native"

# remove all depends normally required for oe_make, e.g. cross-compiler
# and tools: these are not needed for Android
BASEDEPENDS = ""

DEPENDS += "${JDK_VER} lz4-native bc-native python-pycrypto-native curl-native rsync-native bison-native"

ANDROID_PRODUCT ?= "aosp_arm64"
ANDROID_VARIANT ?= "eng"
SOC_FAMILY ?= "default"

JAVA_HOME = "${STAGING_LIBDIR_JVM_NATIVE}/${JDK_VER}"

ANDROID_OUT_DIR_COMMON_BASE = ""
DDK_KM_PREBUILT_MODULE ?= ""
TARGET_PREBUILT_KERNEL ?= ""
DDK_UM_PREBUILDS ?= ""

do_configure() {
}

do_compile() {
    cd ${S}

    # run Android build in sane environment
    if [ ! -f ${S}/.android_signature ]; then
      env -i HOME="$HOME" LC_CTYPE="${LC_ALL:-${LC_CTYPE:-$LANG}}" USER="$USER" \
             PATH="${JAVA_HOME}/bin:$PATH" \
             JAVA_HOME="${JAVA_HOME}" OUT_DIR_COMMON_BASE="${ANDROID_OUT_DIR_COMMON_BASE}" \
             DDK_KM_PREBUILT_MODULE="${DDK_KM_PREBUILT_MODULE}" \
             TARGET_PREBUILT_KERNEL="${TARGET_PREBUILT_KERNEL}" \
             DDK_UM_PREBUILDS="${DDK_UM_PREBUILDS}" \
             bash -c "source build/envsetup.sh && \
                      lunch ${ANDROID_PRODUCT}-${ANDROID_VARIANT} && \
                      make ${EXTRA_OEMAKE} ${PARALLEL_MAKE} \
             "
      build_signature=$(md5sum ${S}/Makefile)
      echo ${build_signature} > ${S}/.android_signature
   else
      echo "${S}/.android_signature exists, no needs to rebuild."
   fi
}
