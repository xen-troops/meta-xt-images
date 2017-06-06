# Do not support secure environment
IMAGE_INSTALL_remove = " \
    optee-linuxdriver \
    optee-linuxdriver-armtz \
    optee-client \
"

IMAGE_INSTALL_append = " \
    nftables \
    dhcp-client \
"

CORE_IMAGE_BASE_INSTALL_remove += "gtk+3-demo clutter-1.0-examples"
