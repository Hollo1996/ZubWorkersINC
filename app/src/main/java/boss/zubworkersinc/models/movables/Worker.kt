package boss.zubworkersinc.models.movables

import boss.zubworkersinc.basics.Direction
import boss.zubworkersinc.controls.ControlInterface
import boss.zubworkersinc.controls.Liquid
import boss.zubworkersinc.graphics.base.MovableRepresentation
import boss.zubworkersinc.graphics.base.WorkerRepresentation
import boss.zubworkersinc.upperleayer.Game
import boss.zubworkersinc.models.map.Field

class Worker(val strength: Double, _representation: WorkerRepresentation) : Movable {

	var inDirection = Direction.UP
	var owner: ControlInterface? = null
	var underThis: Field? = null
	override val representation: MovableRepresentation = _representation

	init {
		_representation.Owner = this
	}


	// The player pushed the worker in a direction,
	// the field's leaveRequest function  is responsible for handling this
	fun Move(direction: Direction) {
		inDirection = direction
		underThis?.LeaveRequest(direction, this)
		//println("invalidate Move")
		Game.graphicFactory?.getGraphicLoader()?.invalidate()
	}

	override fun loadRepresentation() {
		(representation as WorkerRepresentation).InDirection = inDirection
	}

	override fun setField(field: Field) {
		underThis = field
	}

	override fun destroy() {
		underThis?.RemoveMoveable()
		owner?.RemoveWorker(this)
		//println("invalidate Destroy")
		Game.graphicFactory?.getGraphicLoader()?.invalidate()
	}

	override fun accept(v: MovableVisitor) {
		v.visit(this)
	}

	override fun pushed(by: Box, to: Field?) {
		if (to == null || to.GetIsolated() || !to.IsEmpty()) {
			underThis?.DestroyMoveable()
		}
	}

	override fun pushed(by: Worker, to: Field?) {
	}

	fun getName(): String? {
		return owner?.Name
	}


	fun addLiquid(liquid: Liquid) {
		if (liquid.friction > 0) {
			underThis?.rep?.friction = liquid.friction
		}
		//println("invalidate AddLiquid")
		Game.graphicFactory?.getGraphicLoader()?.invalidate()
	}

	// Prints out the worker's attributes to the console
	//fun Print(point: Int) = println("(" + underThis?.position?.column + ";" + underThis?.position?.line + ") " + strength + " " + inDirection + " " + point)
}