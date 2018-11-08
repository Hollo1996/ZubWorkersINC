package boss.zubworkersinc.graphics.base

import boss.zubworkersinc.model.moveables.Box


interface BoxRepresentation:MoveableRepresentation {
	var Owner: Box
	var pushedBy: String?
}