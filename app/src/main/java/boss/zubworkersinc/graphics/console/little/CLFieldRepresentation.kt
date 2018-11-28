package boss.zubworkersinc.graphics.console.little

import boss.zubworkersinc.basics.Direction
import boss.zubworkersinc.basics.Position
import boss.zubworkersinc.controls.Liquid
import boss.zubworkersinc.graphics.EasyWeightMap
import boss.zubworkersinc.graphics.base.FieldRepresentation
import boss.zubworkersinc.models.ModelContainer
import boss.zubworkersinc.models.map.Field
import boss.zubworkersinc.models.movables.Movable

class CLFieldRepresentation : FieldRepresentation, CLRepresentation {
    enum class wallType{
        NONE,WALL,SYNC,ASYNC
    }

    override lateinit var Data: Field.RepresentableData

    companion object {
        val portalSide = '~'
        val syncronPortal = 'O'
        val asyncronPortal = '@'
        val wallH = '-'
        val wallV = '|'
        val honey = 'm'
        val oil = '~'
        val corner = '+'
        val empty = ' '
        private val verticalWalls: Array<Char>
            get() = arrayOf(empty, wallV, syncronPortal, asyncronPortal)
        private val horizontalWalls: Array<EasyWeightMap<Char>>
            get() = arrayOf(
                EasyWeightMap(arrayOf(empty, empty, empty), 3, ' '),
                EasyWeightMap(arrayOf(wallH, wallH, wallH), 3, ' '),
                EasyWeightMap(arrayOf(portalSide, syncronPortal, portalSide), 3, ' '),
                EasyWeightMap(arrayOf(portalSide, asyncronPortal, portalSide), 3, ' ')
                )
        private val defRepresentation: EasyWeightMap<Char>
            get() = EasyWeightMap(
                arrayOf(
                    corner, wallH, wallH, wallH, corner,
                    wallV, empty, empty, empty, wallV,
                    corner, wallH, wallH, wallH, corner
                ), 5, ' '
            )
    }

    private var _representation = defRepresentation

    var start = true

    override var representation = _representation
        get() {
            synchronized(_representation) {
                synchronized(Data.modified) {
                    Data.modified = false
                }

                _representation.drawOn(defRepresentation, Position())

                if (Data.functionality != null)
                    _representation[1, 1] =
                            ((Data.functionality!!.representation) as CLRepresentation).representation[0]

                if (Data.onThis != null)
                    _representation[1, 2] =
                            ((Data.onThis as Movable).representation as CLRepresentation).representation[0]

                if (Data.friction == Liquid.Honey.friction)
                    _representation[1, 3] = honey
                else if (Data.friction == Liquid.Oil.friction)
                    _representation[1, 3] = oil


                if (start) {
                    var d: Direction
                    val selfPosition: Position = Data.Self!!.position
                    var neighbourPosition: Position
                    var index: wallType

                    for (i in arrayOf(0, 4, 10, 14))
                        _representation[i] = corner

                    for (i in 1..4) {
                        d = Direction.values()[i]
                        if (!Data.IsIsolated && (Data.Self as Field)[d] != null) {
                            neighbourPosition = (Data.Self as Field)[d] as Position
                            if (neighbourPosition.column != selfPosition.column + d.vector.column ||
                                neighbourPosition.line != selfPosition.line + d.vector.line
                            ) {
                                if (ModelContainer.fieldsMap[neighbourPosition]!![d.GetReverse()] != null &&
                                    ModelContainer.fieldsMap[neighbourPosition]!![d.GetReverse()] == selfPosition
                                )
                                    index = wallType.SYNC
                                else
                                    index = wallType.ASYNC
                            } else
                                index = wallType.NONE
                        } else {
                            index = wallType.WALL
                        }



                        if ((d.ordinal - 1) % 2 == 0) {
                            _representation.drawOn(horizontalWalls[index.ordinal],Position(1,1 + d.vector.line))
                        } else {
                            _representation[1,2 + (d.vector.column * 2)] = verticalWalls[index.ordinal]
                        }
                    }
                    start = false
                }
                return _representation
            }
        }
}