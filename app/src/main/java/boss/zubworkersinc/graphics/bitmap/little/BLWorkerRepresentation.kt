package boss.zubworkersinc.graphics.bitmap.little

import boss.zubworkersinc.basics.Direction
import boss.zubworkersinc.graphics.base.WorkerRepresentation
import boss.zubworkersinc.graphics.bitmap.EasyWeightBitMap
import boss.zubworkersinc.model.moveables.Worker
import java.util.*

class BLWorkerRepresentation : WorkerRepresentation, BLRepresentation {
    override lateinit var Owner: Worker
    override lateinit var InDirection: Direction
    private var idColor = Random().nextInt()

    companion object {
        private val forGColor = 0x8B4513.toInt()
        private val backGColor = 0x00000000.toInt()
        private val _representationUP = EasyWeightBitMap(
            intArrayOf(
                forGColor, backGColor, forGColor,
                forGColor, backGColor, forGColor,
                forGColor, forGColor, forGColor
            ), 3
        )
        private val _representationRIGHT = EasyWeightBitMap(
            intArrayOf(
                forGColor, forGColor, forGColor,
                forGColor, backGColor, backGColor,
                forGColor, forGColor, forGColor
            ), 3
        )
        private val _representationDOWN = EasyWeightBitMap(
            intArrayOf(
                forGColor, forGColor, forGColor,
                forGColor, backGColor, forGColor,
                forGColor, backGColor, forGColor
            ), 3
        )
        private val _representationLEFT = EasyWeightBitMap(
            intArrayOf(
                forGColor, forGColor, forGColor,
                backGColor, backGColor, forGColor,
                forGColor, forGColor, forGColor
            ), 3
        )
    }

    override var representation = _representationUP
        get() {
            Owner.LoadRepresentation()
            when (InDirection) {
                Direction.UP -> {
                    _representationUP[7] = idColor
                    return _representationUP
                }
                Direction.DOWN -> {
                    _representationRIGHT[3] = idColor
                    return _representationRIGHT
                }
                Direction.LEFT -> {
                    _representationDOWN[2] = idColor
                    return _representationDOWN
                }
                Direction.RIGHT -> {
                    _representationLEFT[5] = idColor
                    return _representationLEFT
                }
                Direction.NONE -> {
                    _representationUP[7] = idColor
                    return _representationUP
                }
            }


        }
}