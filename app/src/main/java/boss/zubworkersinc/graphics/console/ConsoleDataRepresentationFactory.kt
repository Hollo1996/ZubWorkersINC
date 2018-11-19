package boss.zubworkersinc.graphics.console

import boss.zubworkersinc.graphics.base.BoxRepresentation
import boss.zubworkersinc.graphics.base.DataRepresentationFactory
import boss.zubworkersinc.graphics.base.WorkerRepresentation
import boss.zubworkersinc.graphics.console.little.CLBoxRepresentation
import boss.zubworkersinc.graphics.console.little.CLFieldRepresentation
import boss.zubworkersinc.graphics.console.little.CLWorkerRepresentation

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