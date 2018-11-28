package boss.zubworkersinc.graphics.base

import boss.zubworkersinc.basics.Direction
import boss.zubworkersinc.models.movables.Worker

interface WorkerRepresentation:MovableRepresentation {
	var Owner: Worker
	var InDirection: Direction
}