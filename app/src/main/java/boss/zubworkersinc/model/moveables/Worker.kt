package boss.zubworkersinc.model.moveables

import boss.zubworkersinc.basics.Direction
import boss.zubworkersinc.controls.ControlInterface
import boss.zubworkersinc.controls.Liquid
import boss.zubworkersinc.graphics.base.MoveableRepresentation
import boss.zubworkersinc.graphics.base.WorkerRepresentation
import boss.zubworkersinc.upperleayer.Game
import boss.zubworkersinc.model.map.Field

class Worker(val strength: Double, _representation: WorkerRepresentation) : Moveable {
	var inDirection = Direction.UP
	var owner: ControlInterface? = null
	var underThis: Field? = null
	override val representation: MoveableRepresentation = _representation

	init {
		_representation.Owner = this
	}


	// The player pushed the worker in a direction,
	// the field's leaveRequest function  is responsible for handling this
	fun Move(direction: Direction) {
		inDirection = direction
		underThis?.LeaveRequest(direction, this)
		//println("Invalidate Move")
		Game.graphicLoader?.Invalidate()
	}

	override fun LoadRepresentation() {
		(representation as WorkerRepresentation).InDirection = inDirection
	}

	override fun SetField(field: Field) {
		underThis = field
	}

	override fun Destroy() {
		underThis?.RemoveMoveable()
		owner?.RemoveWorker(this)
		//println("Invalidate Destroy")
		Game.graphicLoader?.Invalidate()
	}

	override fun Accept(v: MoveableVisitor) {
		v.Visit(this)
	}

	override fun Pushed(by: Box, to: Field?) {
		if (to == null || to.GetIsolated() || !to.IsEmpty()) {
			underThis?.DestroyMoveable()
		}
	}

	override fun Pushed(by: Worker, to: Field?) {
	}

	fun GetName(): String? {
		return owner?.Name
	}


	fun AddLiquid(liquid: Liquid) {
		if (liquid.friction > 0) {
			underThis?.rep?.friction = liquid.friction
		}
		//println("Invalidate AddLiquid")
		Game.graphicLoader?.Invalidate()
	}

	// Sets an owner for the worker, Gets the control interface as a parameter
	fun SetOwner(controlInterface: ControlInterface) {
		if (this.owner == null)
			this.owner = controlInterface
	}

	// Prints out the worker's attributes to the console
	//fun Print(point: Int) = println("(" + underThis?.position?.column + ";" + underThis?.position?.line + ") " + strength + " " + inDirection + " " + point)
}