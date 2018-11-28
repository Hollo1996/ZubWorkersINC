package boss.zubworkersinc.graphics.console.little

import boss.zubworkersinc.basics.Direction
import boss.zubworkersinc.graphics.EasyWeightMap
import boss.zubworkersinc.graphics.base.WorkerRepresentation
import boss.zubworkersinc.models.movables.Worker

class CLWorkerRepresentation : WorkerRepresentation, CLRepresentation {
	override lateinit var Owner: Worker
	override lateinit var InDirection: Direction
	private var _representation = EasyWeightMap(Array(1) { 'W' }, 1, ' ')
	override var representation = _representation
		get() {
			Owner.loadRepresentation()
			when (InDirection) {
				Direction.UP -> _representation[0] = 'W'
				Direction.DOWN -> _representation[0] = 'M'
				Direction.LEFT -> _representation[0] = '3'
				Direction.RIGHT -> _representation[0] = 'E'
				Direction.NONE -> _representation[0] = 'X'
			}
			return _representation

		}
}