def get_reconstruct_dir(d):
    return d.getVar("XT_RECONSTRUCT_DIR") or ""

def get_manifest_fname(d):
    pn = d.getVar('PN')
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

xt_reconstruct_save_manifest() {
    cd ${S}

    HISTORY_DIR="${BUILDHISTORY_DIR}/${PN}"
    repo manifest --output-file="${HISTORY_DIR}/${PN}.xml"
}

addtask save_manifest after do_populate_sstate_cache before do_build
python do_save_manifest() {
    # check if we are collecting build history
    if not is_buildhistory_enabled(d):
        bb.debug(1, "Build history is not enabled, not populating manifest")
        return

    # we are running a build with buildhistory enabled, but check
    # if this is not a reconstruct build
    if need_to_reconstruct(d):
        return

    # build history + not reconstruction: go ahead and save the manifest
    bb.build.exec_func("xt_reconstruct_save_manifest", d)
}
