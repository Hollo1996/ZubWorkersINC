package boss.zubworkersinc.controls.base

import boss.zubworkersinc.basics.LifeCicle
import boss.zubworkersinc.controls.ControlInterface

abstract class Control: LifeCicle() {
    abstract var Interface: ControlInterface
    abstract fun readKeyLoop()
}