SUMMARY = "Distributed version control system."
HOMEPAGE = "https://launchpad.net/bzr"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING.txt;md5=751419260aa954499f7abaabaa882bbe"
SRC_URI = "https://launchpad.net/${PN}/2.7/${PV}/+download/${PN}-${PV}.tar.gz"
SRC_URI[md5sum] = "8e5020502efd54f5925a14a456b88b89"
SRC_URI[sha256sum] = "0d451227b705a0dd21d8408353fe7e44d3a5069e6c4c26e5f146f1314b8fdab3"

DEPENDS = "python-native"
PROVIDES += "bzr-native"

inherit native pythonnative

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install () {
    python setup.py install
}
