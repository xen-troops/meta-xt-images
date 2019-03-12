FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append = " \
    file://0001-lib-randutils.c-Fix-args-of-getrandom.patch \
"
