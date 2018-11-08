package boss.zubworkersinc.model.moveables

import boss.zubworkersinc.graphics.base.BoxRepresentation
import boss.zubworkersinc.graphics.base.MoveableRepresentation
import boss.zubworkersinc.model.ModelContainer
import boss.zubworkersinc.model.map.Field

class Box(_representation: BoxRepresentation) : Moveable {

	var pushedBy: String? = null
	lateinit var underThis: Field

	override val representation: MoveableRepresentation = _representation

	init {
		_representation.Owner = this
	}

	fun Isolate() {
		underThis.Isolate()
	}

	override fun LoadRepresentation() {
		(representation as BoxRepresentation).pushedBy=pushedBy
	}

	override fun SetField(field: Field) {
		underThis = field
	}

	override fun Destroy() {
		underThis.RemoveMoveable()
		ModelContainer.boxes.remove(this)
	}

	override fun Accept(v: MoveableVisitor) {
		v.Visit(this)
	}

	override fun Pushed(by: Box, to: Field?) {
		pushedBy = by.pushedBy
	}

	override fun Pushed(by: Worker, to: Field?) {
		this.pushedBy = by.GetName()
	}

	fun Peint() = println("(" + underThis.position.column + ";" + underThis.position.line + ") " + pushedBy)
}