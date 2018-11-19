package boss.zubworkersinc.graphics.bitmap

import boss.zubworkersinc.graphics.base.BoxRepresentation
import boss.zubworkersinc.graphics.base.DataRepresentationFactory
import boss.zubworkersinc.graphics.base.FieldRepresentation
import boss.zubworkersinc.graphics.base.WorkerRepresentation
import boss.zubworkersinc.graphics.bitmap.little.BLBoxRepresentation
import boss.zubworkersinc.graphics.bitmap.little.BLFieldRepresentation
import boss.zubworkersinc.graphics.bitmap.little.BLWorkerRepresentation

class BitmapDataRepresentationFactory : DataRepresentationFactory {
	var Big=false
	override fun getWorkerdRep(): WorkerRepresentation {
		return BLWorkerRepresentation()
	}
	override fun getBoxdRep(): BoxRepresentation {
		return BLBoxRepresentation()
	}
	override fun getFieldRep(): FieldRepresentation {
		return BLFieldRepresentation()
	}
}