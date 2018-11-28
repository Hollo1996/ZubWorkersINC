package boss.zubworkersinc.controls.text

import boss.zubworkersinc.controls.base.Control
import boss.zubworkersinc.controls.base.ControlFactory

object TControlFactory :ControlFactory {
    override fun getControl(): Control=TControl
}