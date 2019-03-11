DEFAULTTUNE ?= "cortexa57-cortexa53"
require conf/machine/include/tune-cortexa57-cortexa53.inc
require conf/machine/include/${SOC_FAMILY}.inc

# 32BIT package install (default is disable)
# This variables can be used only in multilib.
USE_32BIT_PKGS ?= "0"
USE_32BIT_WAYLAND ?= "0"
USE_32BIT_MMP ?= "0"

MACHINE_FEATURES = "usb3"

KERNEL_IMAGETYPE = "Image"
IMAGE_FSTYPES = "tar.bz2"

SERIAL_CONSOLE ?= "115200 ttySC0"

# Configuration for kernel
PREFERRED_PROVIDER_virtual/kernel = "linux-renesas"

# Add variable to Build Configuration in build log
BUILDCFG_VARS_append = " SOC_FAMILY"
