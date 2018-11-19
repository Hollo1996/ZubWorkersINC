package boss.zubworkersinc.model.features

import boss.zubworkersinc.graphics.base.FieldRepresentation
import boss.zubworkersinc.model.moveables.Moveable

open class Feature(val representation: FieldRepresentation,val Interact: (m:Moveable) -> Unit)