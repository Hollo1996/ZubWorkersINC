package boss.zubworkersinc.graphics.console.little

import boss.zubworkersinc.graphics.base.BoxRepresentation
import boss.zubworkersinc.model.moveables.Box

class CLBoxRepresentation : BoxRepresentation, CLRepresentation {
    override lateinit var Owner: Box
    override var pushedBy: String? = null
    private var _representation = arrayOf<Array<Char>>(arrayOf('W'))
    override var representation = _representation
        get() {
            Owner.LoadRepresentation()
            var pusherIndex = -1
            TODO("""if (pushedBy!=null&& GameField.relavantControl!!.Name.compareTo(pushedBy as String) == 0)
                pusherIndex = 0""")
            if (pusherIndex >= 0)
                _representation[0][0] = pusherIndex.toString().toCharArray()[0]
            else
                _representation[0][0] = 'H'

            return _representation

        }
}