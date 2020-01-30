DESCRIPTION = "Generic recipe for AArch64 kernels"

inherit kernel
require recipes-kernel/linux/linux-yocto.inc

# Skip processing of this recipe if it is not explicitly specified as the
# PREFERRED_PROVIDER for virtual/kernel. This avoids errors when trying
# to build multiple virtual/kernel providers, e.g. as dependency of
# core-image-rt-sdk, core-image-rt.
python () {
    if d.getVar("PREFERRED_PROVIDER_virtual/kernel", True) != "linux-generic-armv8":
        raise bb.parse.SkipPackage("Set PREFERRED_PROVIDER_virtual/kernel to linux-generic-armv8 to enable it")
}

KMETA = "kernel-meta"

LINUX_KERNEL_TYPE = "tiny"
LINUX_VERSION ?= "4.14.0"

PV = "${LINUX_VERSION}+git${SRCPV}"

COMPATIBLE_MACHINE = "generic-armv8-xt"
