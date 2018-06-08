# Call getty directly while using hvc0
do_install_append() {
    if echo "${DISTRO_FEATURES}" | grep -q 'virtualization'; then
        sed -i "s/start_getty/getty/; s/vt102/xterm/" ${D}${sysconfdir}/inittab
    fi
}
