package boss.zubworkersinc.console.little

import boss.zubworkersinc.basics.Direction
import boss.zubworkersinc.basics.Position
import boss.zubworkersinc.controls.Liquid
import boss.zubworkersinc.graphics.base.FieldRepresentation
import boss.zubworkersinc.model.ModelContainer
import boss.zubworkersinc.model.map.Field
import boss.zubworkersinc.model.moveables.Moveable

class CLFieldRepresentation : FieldRepresentation, CLRepresentation {
    override lateinit var Data: Field.RepresentableData

    var start = true

    companion object {
        val horizontalOuterWalls = arrayOf(' ', '-', '~', '~')
        val horizontalInnerWalls = arrayOf(' ', '-', 'O', '@')
        val verticalWalls = arrayOf(' ', '|', 'O', '@')
        val liquid = arrayOf('m', '~')
        val corner = '+'
        val empty = ' '
    }

    private var _representation = arrayOf<Array<Char>>(
            arrayOf<Char>('*', '-', '-', '-', '*'),
            arrayOf<Char>('|', ' ', ' ', ' ', '|'),
            arrayOf<Char>('*', '-', '-', '-', '*'))


    override var representation=_representation
        get() {
            synchronized(_representation) {
                synchronized(Data.modified) {
                    Data.modified = false
                }

                if (Data.functionality != null)
                    _representation[1][1] = ((Data.functionality!!.representation) as CLRepresentation).representation[0][0]
                else
                    _representation[1][1] = empty

                if (Data.onThis != null)
                    _representation[1][2] = ((Data.onThis as Moveable).representation as CLRepresentation).representation[0][0]
                else
                    _representation[1][2] = empty

                if (Data.friction == Liquid.Honey.friction)
                    _representation[1][3] = liquid[0]
                else if (Data.friction == Liquid.Oil.friction)
                    _representation[1][3] = liquid[1]
                else
                    _representation[1][3] = empty


                //_representation[1][1]=Data.Self?.position?.column.toString()[0]
                //_representation[1][3]=Data.Self?.position?.line.toString()[0]


                if (start) {
                    var d: Direction
                    var selfPosition: Position = Data.Self!!.position
                    var neighbourPosition: Position
                    var index: Int

                    for (i in 0..3)
                        _representation[(i / 2) * 2][(((i + 1) % 4) / 2) * 4] = corner

                    for (i in 1..4) {
                        d = Direction.values()[i]
                        if (!Data.IsIsolated && (Data.Self as Field)[d]!=null) {
                            neighbourPosition = (Data.Self as Field)[d] as Position
                            if (neighbourPosition.column != selfPosition.column + d.vector.column ||
                                    neighbourPosition.line != selfPosition.line + d.vector.line) {
                                if (ModelContainer.fieldsMap[neighbourPosition]!![d.GetReverse()]!=null &&
                                        ModelContainer.fieldsMap[neighbourPosition]!![d.GetReverse()] == selfPosition)
                                    index = 2
                                else
                                    index = 3
                            } else
                                index = 0
                        } else {
                            index = 1
                        }



                        if ((d.ordinal-1) % 2 == 0) {
                            _representation[1 + d.vector.line][1] = horizontalOuterWalls[index]
                            _representation[1 + d.vector.line][2] = horizontalInnerWalls[index]
                            _representation[1 + d.vector.line][3] = horizontalOuterWalls[index]
                        } else {
                            _representation[1][2 + (d.vector.column * 2)] = verticalWalls[index]
                        }
                    }
                    start = false
                }
                return _representation
            }
        }
}