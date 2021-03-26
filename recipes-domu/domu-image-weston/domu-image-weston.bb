SUMMARY = "Guest image with weston"

require inc/domx-image-weston.inc

XT_QUIRK_UNPACK_SRC_URI += " \
    file://meta-xt-images-vgpu;subdir=repo \
"

# these layers will be added to bblayers.conf on do_configure
XT_QUIRK_BB_ADD_LAYER += " \
    meta-xt-images-vgpu \
    meta-arm/meta-arm \
"
