DESCRIPTION = "Demo application to showcase 3D graphics using kms and gbm"
HOMEPAGE = "https://cgit.freedesktop.org/mesa/kmscube/"
LICENSE = "MIT"
SECTION = "graphics"
DEPENDS = "mesa virtual/libgles2 virtual/egl libdrm libgbm gstreamer1.0 gstreamer1.0-plugins-base"

LIC_FILES_CHKSUM = "file://kmscube.c;beginline=1;endline=23;md5=8b309d4ee67b7315ff7381270dd631fb"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRCREV = "8c6a20901f95e1b465bbca127f9d47fcfb8762e6"
SRC_URI = "\
    git://anongit.freedesktop.org/mesa/kmscube;branch=master;protocol=git \
    file://0001-Add-R-Car-and-Xen-display-units.patch \
    "

S = "${WORKDIR}/git"

inherit autotools pkgconfig features_check

REQUIRED_DISTRO_FEATURES = "opengl"
