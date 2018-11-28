package boss.zubworkersinc.models.features

import boss.zubworkersinc.graphics.base.FieldRepresentation
import boss.zubworkersinc.models.movables.Movable

open class Feature(val representation: FieldRepresentation,val Interact: (m: Movable) -> Unit)