package boss.zubworkersinc.models.movables

import boss.zubworkersinc.graphics.base.MovableRepresentation
import boss.zubworkersinc.models.map.Field

interface Movable {
	val representation: MovableRepresentation

	abstract fun loadRepresentation()

	// Stores the given Field for the Movable object as an attribute called "underThis"
	abstract fun setField(field: Field)

	// Destroys the Movable object
	abstract fun destroy()

	// Visitor pattern function
	abstract fun accept(v: MovableVisitor)

	// A Movable object Gets pushed by a box, to a given field
	abstract fun pushed(by: Box, to: Field?)

	// A Movable object Gets pushed by a worker, to a given field
	abstract fun pushed(by: Worker, to: Field?)

}