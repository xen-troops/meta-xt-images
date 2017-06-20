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

def check_url_or_pack(url, type, location, d):
    if type.lower() != 'file':
        return url
    if not os.path.isdir(location):
        return url
    new_location = create_tarfile(location, d)
    return url.replace(location, new_location)

def remove_tarfile(url, type, location, d):
    if type.lower() != 'file':
        return
    if not os.path.isdir(location):
        return
    arcname = get_tarfname(location, d)
    if os.path.exists(arcname):
        os.remove(arcname)

addtask unpack_xt_extras after do_unpack before do_configure
python do_unpack_xt_extras() {
    bb.debug(1, "Unpacking Xen-troops extras")
    # all the sources in SRC_URI have been unpacked already, clean it
    d.setVar("SRC_URI", "")
    # now deliver all the static extras into inner build system
    urls = (d.getVar("XT_QUIRK_UNPACK_SRC_URI") or "").split()
    for url in urls:
        type, _, location, _, _, _ = bb.fetch.decodeurl(url)
        item = check_url_or_pack(url, type, location, d) + " "
        d.appendVar("SRC_URI", item or "")

    # now call real unpacker to do the rest
    bb.build.exec_func('base_do_unpack', d)

    # remove the archives we created
    urls = (d.getVar("XT_QUIRK_UNPACK_SRC_URI") or "").split()
    for url in urls:
        type, _, location, _, _, _ = bb.fetch.decodeurl(url)
        remove_tarfile(url, type, location, d)
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
