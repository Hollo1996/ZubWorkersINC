package boss.zubworkersinc.graphics.bitmap.little

import android.graphics.Color
import boss.zubworkersinc.basics.Direction
import boss.zubworkersinc.graphics.EasyWeightMap
import boss.zubworkersinc.graphics.base.WorkerRepresentation
import boss.zubworkersinc.models.movables.Worker
import java.util.*

class BLWorkerRepresentation : WorkerRepresentation, BLRepresentation {
    override lateinit var Owner: Worker
    override lateinit var InDirection: Direction
    private var idColor = Random().nextInt()

    companion object {
        private val forGColor = Color.GREEN
        private val backGColor = Color.TRANSPARENT
        private val representationUP = EasyWeightMap(
            arrayOf(
                backGColor, forGColor, backGColor,
                backGColor, forGColor, backGColor,
                backGColor, backGColor, backGColor
            ), 3, 0
        )
        private val representationRIGHT = EasyWeightMap(
            arrayOf(
                backGColor, backGColor, backGColor,
                backGColor, forGColor, forGColor,
                backGColor, backGColor, backGColor
            ), 3, 0
        )
        private val representationDOWN = EasyWeightMap(
            arrayOf(
                backGColor, backGColor, backGColor,
                backGColor, forGColor, backGColor,
                backGColor, forGColor, backGColor
            ), 3, 0
        )
        private val representationLEFT = EasyWeightMap(
            arrayOf(
                backGColor, backGColor, backGColor,
                forGColor, forGColor, backGColor,
                backGColor, backGColor, backGColor
            ), 3, 0
        )
    }

    override var representation = representationUP
        get() {
            Owner.loadRepresentation()
            when (InDirection) {
                Direction.UP -> {
                    representationUP[7] = idColor
                    return representationUP
                }
                Direction.DOWN -> {
                    representationRIGHT[3] = idColor
                    return representationRIGHT
                }
                Direction.LEFT -> {
                    representationDOWN[2] = idColor
                    return representationDOWN
                }
                Direction.RIGHT -> {
                    representationLEFT[5] = idColor
                    return representationLEFT
                }
                Direction.NONE -> {
                    representationUP[7] = idColor
                    return representationUP
                }
            }


        }
}