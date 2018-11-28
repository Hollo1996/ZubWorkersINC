package boss.zubworkersinc.graphics.console.little

import boss.zubworkersinc.graphics.base.BoxRepresentation
import boss.zubworkersinc.graphics.EasyWeightMap
import boss.zubworkersinc.models.movables.Box

class CLBoxRepresentation : BoxRepresentation, CLRepresentation {
    override lateinit var Owner: Box
    override var pushedBy: String? = null
    companion object {
        private var _representation = EasyWeightMap(Array(1) { 'H' }, 1, ' ')
    }
    override var representation = _representation
        get() {
            Owner.loadRepresentation()
            val pusherIndex = -1
            if (pusherIndex >= 0)
                _representation[0] = pusherIndex.toString().toCharArray()[0]
            else
                _representation[0] = 'H'

            return _representation

        }
}