#Add Xen to build
IMAGE_INSTALL_append = " \
    xen-base \
    xen-flask \
    guests \
    libxenbe \
    displbe \
"
IMAGE_INSTALL_append_salvator-x-domx = " \
    guest-images \
"

# Do not support secure environment
IMAGE_INSTALL_remove = " \
    optee-linuxdriver \
    optee-linuxdriver-armtz \
    optee-client \
"

populate_append() {
	install -m 0644 ${DEPLOY_DIR_IMAGE}/xen-${MACHINE}.gz ${DEST}/xen.gz
}

IMAGE_INSTALL_append = " \
    nftables \
    dhcp-client \
"

CORE_IMAGE_BASE_INSTALL_remove += "gtk+3-demo clutter-1.0-examples"
