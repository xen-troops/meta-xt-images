def get_reconstruct_dir(d):
    return d.getVar("XT_RECONSTRUCT_DIR") or ""

def get_manifest_fname(d):
    pn = d.getVar('PN')
    if pn == "android":
        return os.path.join(pn + ".xml")
    return os.path.join(pn , pn + ".xml")

def is_buildhistory_enabled(d):
    return (d.getVar("BUILDHISTORY_COMMIT") or "") == "1"

def need_to_reconstruct(d):
    bb.debug(1, "Checking if build reconstruction was requested")

    reconstruct_dir = get_reconstruct_dir(d)
    if not reconstruct_dir:
        bb.debug(1, "Not a reconstructtion build")
    return reconstruct_dir

python do_unpack_append() {
    import os.path

    # check if we are running a reconstrion build
    reconstruct_dir = need_to_reconstruct(d)
    if not reconstruct_dir:
        return

    # now get the manifest from build history
    manifest_fname = os.path.join(reconstruct_dir, get_manifest_fname(d))
    bb.debug(1, "Will use saved manifest for reconstruction at " +
        manifest_fname)
    if not os.path.isfile(manifest_fname):
        bb.debug(1, "Saved manifest not found: " + manifest_fname)
        raise

    # sync repo with this manifest
    repo_dir = d.getVar("S") or ""
    if not repo_dir:
        bb.debug(1, "Can't get recipe's source directory, S not set?")
        raise

    bb.fetch2.runfetchcmd("repo sync --force-sync -m %s" % (manifest_fname),
        d, workdir=repo_dir)
}

do_build_prepend() {
    # check if we are collecting build history
    if [ "${BUILDHISTORY_COMMIT}" != "1" ];then
        echo "Build history is not enabled, not populating manifest"
        exit 0
    fi

    # we are running a build with buildhistory enabled, but check
    # if this is not a reconstruct build
    if [ -n "${XT_RECONSTRUCT_DIR}" ];then
        echo "This is a reconstructtion build, not populating manifest"
        exit 0
    fi

    echo "Not a reconstructtion build"
    cd ${S}

    HISTORY_DIR="${BUILDHISTORY_DIR}/${PN}"
    if [ ! -d ${HISTORY_DIR} ];then
        mkdir ${HISTORY_DIR}
    fi

    repo manifest -r --output-file="${HISTORY_DIR}/${PN}.xml"
    ln -sfr ${HISTORY_DIR}/${PN}.xml ${BUILDHISTORY_DIR}/${PN}.xml
}

# We have dependency on do_build task while reconstructing
# an image but recipes which are not an image do not execute it.
# So, let do_build task be executable for such kind of recipes.
python() {
    d.delVarFlag("do_build", "noexec")
}
