package boss.zubworkersinc.controls.key

import boss.zubworkersinc.controls.base.Control
import boss.zubworkersinc.controls.base.ControlFactory

object KControlFactory :ControlFactory {
    override fun getControl(): Control=KControl
}