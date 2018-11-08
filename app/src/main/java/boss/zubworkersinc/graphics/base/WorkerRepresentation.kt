package boss.zubworkersinc.graphics.base

import boss.zubworkersinc.basics.Direction
import boss.zubworkersinc.model.moveables.Worker

interface WorkerRepresentation:MoveableRepresentation {
	var Owner: Worker
	var InDirection: Direction
}