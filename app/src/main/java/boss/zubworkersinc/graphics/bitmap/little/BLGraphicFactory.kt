package boss.zubworkersinc.graphics.bitmap.little

import boss.zubworkersinc.basics.Position
import boss.zubworkersinc.graphics.base.*
import boss.zubworkersinc.graphics.bitmap.BitLoader

object BLGraphicFactory : GraphicFactory<Int> {

    override fun getGraphicLoader(): GraphicLoader<Int> = BitLoader

    override fun getTileSize(): Position = Position(5, 5)

    override fun getWorkerRep(): WorkerRepresentation = BLWorkerRepresentation()

    override fun getBoxRep(): BoxRepresentation = BLBoxRepresentation()

    override fun getFieldRep(): FieldRepresentation = BLFieldRepresentation()
}