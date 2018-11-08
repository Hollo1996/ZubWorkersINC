package boss.zubworkersinc.model.features

import boss.zubworkersinc.graphics.base.FieldRepresentation
import boss.zubworkersinc.model.moveables.Moveable

interface Feature {
	val representation: FieldRepresentation
	fun Interact(m: Moveable)
}