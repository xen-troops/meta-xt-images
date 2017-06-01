#Add Xen to build
IMAGE_INSTALL_append = " \
    xen-base \
    xen-flask \
    guests \
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

