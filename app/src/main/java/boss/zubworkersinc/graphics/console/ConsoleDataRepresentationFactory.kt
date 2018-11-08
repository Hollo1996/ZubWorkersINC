package boss.zubworkersinc.console

import boss.zubworkersinc.console.little.CLBoxRepresentation
import boss.zubworkersinc.console.little.CLFieldRepresentation
import boss.zubworkersinc.console.little.CLWorkerRepresentation
import boss.zubworkersinc.graphics.base.BoxRepresentation
import boss.zubworkersinc.graphics.base.DataRepresentationFactory
import boss.zubworkersinc.graphics.base.WorkerRepresentation

class ConsoleDataRepresentationFactory : DataRepresentationFactory {
	var Big=false
	override fun getWorkerdRep(): WorkerRepresentation {
		return CLWorkerRepresentation()
	}
	override fun getBoxdRep(): BoxRepresentation {
		return CLBoxRepresentation()
	}
	override fun getFieldRep(): CLFieldRepresentation {
		return CLFieldRepresentation()
	}
}