def get_tarfname(location, d):
    return os.path.join(os.path.dirname(location),
        str(d.getVar('PN')) + "-" + str(d.getVar('PV')) + "-" +
        os.path.basename(location) + ".tar.gz")

def create_tarfile(location, d):
    import tarfile

    arcname = get_tarfname(location, d)
    tar = tarfile.open(arcname, "w:gz")
    tar.add(location, os.path.basename(location))
    tar.close()
    return arcname

addtask unpack_xt_extras after do_unpack before do_configure
python do_unpack_xt_extras() {
    bb.debug(1, "Unpacking Xen-troops extras")
    urls = (d.getVar("XT_QUIRK_UNPACK_SRC_URI") or "").split()

    # if we are reconstructing the build then populate saved recipe revisions
    if xt_is_reconstruct(d):
        versions_path = os.path.join(get_reconstruct_dir(d), d.getVar("PN"))
        d.appendVar("FILESEXTRAPATHS", versions_path or "")
        urls.append("file://" + "build-versions.inc" + ";subdir=repo/build/conf")

    for url in urls:
        try:
            type, _, location, _, _, p = bb.fetch.decodeurl(url)
            path = bb.fetch2.localpath(url, d)
            bb.debug(1, "Make symbolic link for path:" + path)
            rootdir = d.getVar('WORKDIR')
            subdir = d.getVar('S')
            if p.get('subdir') is not None:
                subdir = p['subdir']

            location = os.path.join(subdir, location)
            unpackdir = os.path.join(rootdir, subdir)
            bb.utils.mkdirhier(unpackdir)
            dest = os.path.join(rootdir, location)
            if os.path.exists(dest):
                os.remove(dest)
            os.symlink(os.path.realpath(path), dest)
        except BaseException as error:
            bb.fatal("Failed to create link for path: {} error: {}".format(path, error))
}

python do_patch_prepend() {
    d.appendVar("SRC_URI", "\n")
    urls = (d.getVar("XT_QUIRK_PATCH_SRC_URI") or "").split()
    d.appendVar("SRC_URI", d.getVar("XT_QUIRK_PATCH_SRC_URI") or "")
    try:
        fetcher = bb.fetch2.Fetch(urls, d)
        fetcher.download()
    except bb.fetch2.BBFetchException as e:
        bb.fatal(str(e))

}

def get_reconstruct_dir(d):
    return os.path.join(d.getVar("XT_RECONSTRUCT_DIR") or "",
        d.getVar('PN'))

def xt_is_reconstruct(d):
    bb.debug(1, "Checking if build reconstruction was requested")
    reconstruct_dir = d.getVar("XT_RECONSTRUCT_DIR") or ""

    if not reconstruct_dir:
        bb.debug(1, "Not a reconstructtion build")
        return False

    pn = d.getVar("PN") or ""
    if not os.path.isdir(os.path.join(reconstruct_dir, pn)):
        bb.debug(1, "Nothing to reconstruct")
        return False
    return True

addtask xt_config_reconstruct after do_configure before do_compile
python do_xt_config_reconstruct() {
    if not xt_is_reconstruct(d):
        return

    conf_file = os.path.join(d.getVar("S" or ""), "build/conf/local.conf")
    with open(conf_file, "a") as f:
        f.write("require" + " " + "build-versions.inc" + "\n")
}

do_configure[nostamp] = "1"
do_compile[nostamp] = "1"
