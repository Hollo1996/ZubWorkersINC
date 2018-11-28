package boss.zubworkersinc.graphics.console.little

import boss.zubworkersinc.basics.Position
import boss.zubworkersinc.graphics.base.BoxRepresentation
import boss.zubworkersinc.graphics.base.GraphicFactory
import boss.zubworkersinc.graphics.base.GraphicLoader
import boss.zubworkersinc.graphics.base.WorkerRepresentation
import boss.zubworkersinc.graphics.console.CharLoader

object CLGraphicFactory : GraphicFactory<Char> {

	override fun getGraphicLoader(): GraphicLoader<Char> = CharLoader

	override fun getTileSize(): Position =Position(5,3)
	
	override fun getWorkerRep(): WorkerRepresentation = CLWorkerRepresentation()

	override fun getBoxRep(): BoxRepresentation = CLBoxRepresentation()

	override fun getFieldRep(): CLFieldRepresentation = CLFieldRepresentation()
}