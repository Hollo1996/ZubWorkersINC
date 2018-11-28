package boss.zubworkersinc.controls.console

import boss.zubworkersinc.controls.base.Control
import boss.zubworkersinc.controls.base.ControlFactory

object CControlFactory : ControlFactory {
    override fun getControl(): Control =CControl
}