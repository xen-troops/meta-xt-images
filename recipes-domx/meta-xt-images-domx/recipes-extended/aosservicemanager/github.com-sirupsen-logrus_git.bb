DESCRIPTION = "github.com/sirupsen/logrus"

GO_IMPORT = "github.com/sirupsen/logrus"

inherit go

SRC_URI = "git://${GO_IMPORT};protocol=https;destsuffix=${PN}-${PV}/src/${GO_IMPORT}"
SRCREV = "${AUTOREV}"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://src/${GO_IMPORT}/LICENSE;md5=8dadfef729c08ec4e631c4f6fc5d43a0"

FILES_${PN} += "${GOBIN_FINAL}/*"

do_compile[noexec] = "1"