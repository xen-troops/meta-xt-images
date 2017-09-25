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
