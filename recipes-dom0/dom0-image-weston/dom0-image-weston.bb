SUMMARY = "Image with weston"

LICENSE = "MIT"

inherit build_yocto
inherit xt_quirks

S = "${WORKDIR}/repo"
# path for machine specific layers and recipes
FILESEXTRAPATHS_prepend := "${THISDIR}/../../machine:"

################################################################################
# set to AUTOREV so we can override this while reconstructing this build with
# specific commit ID
################################################################################
SRCREV = "${AUTOREV}"

################################################################################
# All repo:// URLs must have "scmdata=keep" parameter, so we can build history
################################################################################

################################################################################
# Renesas R-Car
################################################################################
SRC_URI_rcar = "repo://github.com/xen-troops/manifests;protocol=https;branch=vgpu-dev;scmdata=keep"
XT_QUIRK_PATCH_SRC_URI_rcar = "file://${S}/meta-renesas/meta-rcar-gen3/docs/sample/patch/patch-for-linaro-gcc/0001-rcar-gen3-add-readme-for-building-with-Linaro-Gcc.patch;patchdir=meta-renesas"
XT_BB_LAYERS_FILE_rcar = "meta-rcar-gen3-xen/doc/bblayers.conf"
XT_BB_LOCAL_CONF_FILE_rcar = "meta-rcar-gen3-xen/doc/local-wayland.conf"
XT_BB_IMAGE_TARGET_rcar = "core-image-weston"

XT_QUIRK_UNPACK_SRC_URI_append_rcar = " file://meta-rcar-gen3-xen;subdir=repo"
# these layers will be added to bblayers.conf on do_configure
XT_QUIRK_BB_ADD_LAYER_append_rcar = " meta-rcar-gen3-xen"

unpack_proprietary_rcar() {
    export PKGS_DIR="${S}/proprietary/rcar/m3_h3_gfx"
    cd ${PKGS_DIR}
    unzip -oq R-Car_Gen3_Series_Evaluation_Software_Package_for_Linux-*.zip
    unzip -oq R-Car_Gen3_Series_Evaluation_Software_Package_of_Linux_Drivers-*.zip
    cd ${S}/meta-renesas
    sh meta-rcar-gen3/docs/sample/copyscript/copy_evaproprietary_softwares.sh -f $PKGS_DIR
}

python do_unpack_append_rcar() {
    bb.build.exec_func('unpack_proprietary_rcar', d)
}

################################################################################
# Generic
################################################################################
XT_BB_IMAGE_TARGET = "core-image-weston"
