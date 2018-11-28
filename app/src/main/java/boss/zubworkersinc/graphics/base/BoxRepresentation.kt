package boss.zubworkersinc.graphics.base

import boss.zubworkersinc.models.movables.Box


interface BoxRepresentation:MovableRepresentation {
	var Owner: Box
	var pushedBy: String?
}