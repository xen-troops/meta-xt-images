rcar_unpack_evaproprietary() {
    export PKGS_DIR="${S}/proprietary/rcar/m3_h3_gfx"
    cd ${PKGS_DIR}
    unzip -oq R-Car_Gen3_Series_Evaluation_Software_Package_for_Linux-*.zip
    unzip -oq R-Car_Gen3_Series_Evaluation_Software_Package_of_Linux_Drivers-*.zip
    cd ${S}/meta-renesas
    sh meta-rcar-gen3/docs/sample/copyscript/copy_evaproprietary_softwares.sh -f $PKGS_DIR
}
