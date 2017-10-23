SUMMARY = "Android"
DESCRIPTION = "Recipe for building Android image"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

S = "${WORKDIR}/repo"

SRCREV = "${AUTOREV}"

require inc/xt_shared_env.inc

inherit java

JDK_VER = "openjdk-8-native"

DEPENDS += "${JDK_VER} lz4-native"

ANDROID_PRODUCT ?= "aosp_arm64"
ANDROID_VARIANT ?= "eng"

ANDROID_CCACHE = "prebuilts/misc/linux-x86/ccache/ccache"
ANDROID_CCACHE_SIZE_GB = "50"

ANDROID_JACK_ADMIN = "prebuilts/sdk/tools/jack-admin"
ANDROID_JACK_MEM_SIZE_GB = "4"

JAVA_HOME = "${STAGING_LIBDIR_JVM_NATIVE}/${JDK_VER}"

do_configure() {
}

do_compile() {
    cd ${S}

    export USE_CCACHE=1
    # keep .ccache aside of Yocto's cache
    export CCACHE_DIR=${SSTATE_DIR}/../${PN}-ccache
    ${ANDROID_CCACHE} -M "${ANDROID_CCACHE_SIZE_GB}G"

    export JACK_SERVER_VM_ARGUMENTS="-Dfile.encoding=UTF-8 -XX:+TieredCompilation -Xmx${ANDROID_JACK_MEM_SIZE_GB}g"
    ${ANDROID_JACK_ADMIN} kill-server || true

    # run Android build in sane environment
    env -i HOME="$HOME" LC_CTYPE="${LC_ALL:-${LC_CTYPE:-$LANG}}" PATH="${JAVA_HOME}/bin:$PATH" USER="$USER" \
           JAVA_HOME="${JAVA_HOME}" \
           bash -c "source build/envsetup.sh && \
                    lunch ${ANDROID_PRODUCT}-${ANDROID_VARIANT} && \
                    make ${EXTRA_OEMAKE} ${PARALLEL_MAKE} \
           "
    # clean up
    ${ANDROID_JACK_ADMIN} kill-server || true
    ${ANDROID_JACK_ADMIN} uninstall-server || true
}

