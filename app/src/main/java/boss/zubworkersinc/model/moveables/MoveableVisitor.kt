package boss.zubworkersinc.model.moveables

interface MoveableVisitor {
	fun Visit(b: Box)
	fun Visit(w: Worker)
}