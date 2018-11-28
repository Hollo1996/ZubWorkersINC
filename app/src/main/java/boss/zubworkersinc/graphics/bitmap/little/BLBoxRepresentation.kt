package boss.zubworkersinc.graphics.bitmap.little

import android.graphics.Color
import boss.zubworkersinc.graphics.base.BoxRepresentation
import boss.zubworkersinc.graphics.EasyWeightMap
import boss.zubworkersinc.models.movables.Box
import java.util.*

class BLBoxRepresentation : BoxRepresentation, BLRepresentation {
    override lateinit var Owner: Box
    override var pushedBy: String? = null
    private var idColor = Random().nextInt()

    companion object {
        private var forGColor = Color.LTGRAY
        private var backGColor = Color.TRANSPARENT
        private val defRepresentation = EasyWeightMap(
            arrayOf(
                backGColor, backGColor, backGColor,
                backGColor, forGColor, backGColor,
                backGColor, backGColor, backGColor
            ), 3, 0
        )
    }

    override var representation = defRepresentation
        get() {
            defRepresentation[4] = idColor
            return defRepresentation
        }
}