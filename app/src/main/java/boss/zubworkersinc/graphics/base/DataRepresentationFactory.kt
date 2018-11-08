package boss.zubworkersinc.graphics.base

interface DataRepresentationFactory {
	fun getFieldRep(): FieldRepresentation
	fun getWorkerdRep(): WorkerRepresentation
	fun getBoxdRep(): BoxRepresentation
}