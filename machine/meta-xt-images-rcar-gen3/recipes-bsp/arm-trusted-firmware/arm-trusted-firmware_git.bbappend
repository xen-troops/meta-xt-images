SRC_URI = "git://github.com/xen-troops/arm-trusted-firmware.git;branch=${BRANCH}"

# Start cores in EL2 mode
ATFW_OPT_append = ' RCAR_BL33_EXECUTION_EL=BL33_EL2'
