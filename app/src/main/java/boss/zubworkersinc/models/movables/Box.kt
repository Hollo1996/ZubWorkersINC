package boss.zubworkersinc.models.movables

import boss.zubworkersinc.graphics.base.BoxRepresentation
import boss.zubworkersinc.graphics.base.MovableRepresentation
import boss.zubworkersinc.models.ModelContainer
import boss.zubworkersinc.models.map.Field

class Box(_representation: BoxRepresentation) : Movable {

	var pushedBy: String? = null
	lateinit var underThis: Field

	override val representation: MovableRepresentation = _representation

	init {
		_representation.Owner = this
	}

	fun Isolate() {
		underThis.Isolate()
	}

	override fun loadRepresentation() {
		(representation as BoxRepresentation).pushedBy=pushedBy
	}

	override fun setField(field: Field) {
		underThis = field
	}

	override fun destroy() {
		underThis.RemoveMoveable()
		ModelContainer.boxes.remove(this)
	}

	override fun accept(v: MovableVisitor) {
		v.visit(this)
	}

	override fun pushed(by: Box, to: Field?) {
		pushedBy = by.pushedBy
	}

	override fun pushed(by: Worker, to: Field?) {
		this.pushedBy = by.getName()
	}

	fun Paint() = println("(" + underThis.position.column + ";" + underThis.position.line + ") " + pushedBy)
}