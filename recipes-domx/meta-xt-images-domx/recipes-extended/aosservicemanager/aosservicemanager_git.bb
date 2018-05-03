DESCRIPTION = "AOS Service Manager"

LICENSE = "CLOSED"

inherit go

FILES_${PN} += "${GOBIN_FINAL}/*"

DEPENDS += "\
	github.com-cavaliercoder-grab \
	github.com-coreos-go-systemd \
	github.com-godbus-dbus \
	github.com-mattn-go-sqlite3 \
	github.com-opencontainers-runtime-spec \
	github.com-sirupsen-logrus \
	github.com-streadway-amqp \
	golang.org-x-crypto \
	golang.org-x-sys \
"
