
inherit ${TOPDIR}/../poky/meta/classes/sstate.bbclass

python () {
    if bb.data.inherits_class('native', d) or bb.data.inherits_class('crosssdk', d) or bb.data.inherits_class('cross', d):
        d.setVar('SSTATE_EXTRAPATH', "../${NATIVELSBSTRING}/")
        d.setVar('SSTATE_EXTRAPATHWILDCARD', "../${NATIVELSBSTRING}/")
}
