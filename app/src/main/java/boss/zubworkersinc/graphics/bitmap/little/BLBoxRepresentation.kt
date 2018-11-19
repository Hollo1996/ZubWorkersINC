package boss.zubworkersinc.graphics.bitmap.little

import boss.zubworkersinc.graphics.base.BoxRepresentation
import boss.zubworkersinc.graphics.bitmap.EasyWeightBitMap
import boss.zubworkersinc.model.moveables.Box
import java.util.*

class BLBoxRepresentation : BoxRepresentation, BLRepresentation {
    override lateinit var Owner: Box
    override var pushedBy: String? = null
    private var forGColor = 0x8B4513.toInt()
    private var backGColor = 0x00000000.toInt()
    private var idColor = Random().nextInt()

    companion object {
        private val forGColor = 0x8B4513.toInt()
        private val backGColor = 0x00000000.toInt()
        private val _representation = EasyWeightBitMap(
            intArrayOf(
                forGColor, backGColor, forGColor,
                forGColor, forGColor, forGColor,
                forGColor, backGColor, forGColor
            ), 3
        )
    }

    override var representation = _representation
        get() {
            _representation[4] = idColor
            return _representation
        }
}