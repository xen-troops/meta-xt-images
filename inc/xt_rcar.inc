EXPANDED_XT_RCAR_EVAPROPRIETARY_DIR = "${@d.getVar('XT_RCAR_EVAPROPRIETARY_DIR') or '""'}"

rcar_unpack_evaproprietary() {
    export PKGS_DIR="${S}/proprietary/rcar/m3_h3_gfx"
    if [ -n "${EXPANDED_XT_RCAR_EVAPROPRIETARY_DIR}" ] ; then
        install -d ${PKGS_DIR}
        cp -rf ${XT_RCAR_EVAPROPRIETARY_DIR}/* ${PKGS_DIR}
    fi
    cd ${PKGS_DIR}
    unzip -oq R-Car_Gen3_Series_Evaluation_Software_Package_for_Linux-*.zip
    unzip -oq R-Car_Gen3_Series_Evaluation_Software_Package_of_Linux_Drivers-*.zip
    cd ${S}/meta-renesas
    sh meta-rcar-gen3/docs/sample/copyscript/copy_evaproprietary_softwares.sh -f $PKGS_DIR
}

xt_unpack_proprietary() {
    export PKGS_DIR="${S}/proprietary/xt_rcar"
    if [ -n "${EXPANDED_XT_RCAR_EVAPROPRIETARY_DIR}" ] ; then
        install -d ${PKGS_DIR}
        cp -rf ${XT_RCAR_EVAPROPRIETARY_DIR}/* ${PKGS_DIR}
    fi
    cd ${PKGS_DIR}
    install -d ${S}/meta-xt-prod-extra/recipes-graphics/rcar-proprietary-graphic/files
    cp r8a779* ${S}/meta-xt-prod-extra/recipes-graphics/rcar-proprietary-graphic/files
}
