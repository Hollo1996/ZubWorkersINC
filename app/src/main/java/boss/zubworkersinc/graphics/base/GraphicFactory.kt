package boss.zubworkersinc.graphics.base

import boss.zubworkersinc.basics.Position

interface GraphicFactory<T:Any> {
	fun getFieldRep(): FieldRepresentation
	fun getWorkerRep(): WorkerRepresentation
	fun getBoxRep(): BoxRepresentation
	fun getTileSize(): Position
	fun getGraphicLoader(): GraphicLoader<T>
}