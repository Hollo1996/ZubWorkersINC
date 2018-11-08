package boss.zubworkersinc.model.moveables

import boss.zubworkersinc.graphics.base.MoveableRepresentation
import boss.zubworkersinc.model.map.Field

interface Moveable {
	val representation: MoveableRepresentation

	abstract fun LoadRepresentation()

	// Stores the given Field for the Moveable object as an attribute called "underThis"
	abstract fun SetField(field: Field)

	// Destroys the Moveable object
	abstract fun Destroy()

	// Visitor pattern function
	abstract fun Accept(v: MoveableVisitor)

	// A Moveable object Gets pushed by a box, to a given field
	abstract fun Pushed(by: Box, to: Field?)

	// A Moveable object Gets pushed by a worker, to a given field
	abstract fun Pushed(by: Worker, to: Field?)

}