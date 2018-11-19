package boss.zubworkersinc.graphics.bitmap.little

import boss.zubworkersinc.basics.Direction
import boss.zubworkersinc.basics.Position
import boss.zubworkersinc.controls.Liquid
import boss.zubworkersinc.graphics.base.FieldRepresentation
import boss.zubworkersinc.graphics.bitmap.EasyWeightBitMap
import boss.zubworkersinc.model.ModelContainer
import boss.zubworkersinc.model.map.Field
import boss.zubworkersinc.model.moveables.Moveable

class BLFieldRepresentation : FieldRepresentation, BLRepresentation {
    override lateinit var Data: Field.RepresentableData

    var start = true
    private var forGColor = 0x8B4513.toInt()
    private var backGColor = 0x00000000.toInt()

    companion object {
        //val horizontalOuterWalls = arrayOf(' ', '-', '~', '~')
        //val horizontalInnerWalls = arrayOf(' ', '-', 'O', '@')
        //val verticalWalls = arrayOf(' ', '|', 'O', '@')
        //val liquid = arrayOf('m', '~')
        //val corner = '+'
        //val empty = ' '
        val empty:Int = 0xFF000000.toInt()
        val portalSide:Int = 0xFFFF00FF.toInt()
        val syncronPortal:Int = 0xFF0000FF.toInt()
        val asyncronPortal:Int = 0xFFFF0000.toInt()
        val wall:Int = 0xFF77777
        val corner:Int = 0xFF777777.toInt()
        val honey:Int = 0x77FFFF00
        val oil:Int = 0x77000000

        private val defRepresentation = EasyWeightBitMap(
            intArrayOf(
                corner, wall, wall, wall, corner,
                wall, empty, empty, empty, wall,
                wall, empty, empty, empty, wall,
                wall, empty, empty, empty, wall,
                corner, wall, wall, wall, corner
            ), 5
        )

        private val horizontalWalls = arrayOf(
            EasyWeightBitMap(intArrayOf(empty, empty, empty), 3),
            EasyWeightBitMap(intArrayOf(wall, wall, wall), 3),
            EasyWeightBitMap(intArrayOf(portalSide, syncronPortal, portalSide), 3),
            EasyWeightBitMap(intArrayOf(portalSide, asyncronPortal, portalSide), 3)
        )
        private val verticalWalls = arrayOf(
            EasyWeightBitMap(intArrayOf(empty, empty, empty), 1),
            EasyWeightBitMap(intArrayOf(wall, wall, wall), 1),
            EasyWeightBitMap(intArrayOf(portalSide, syncronPortal, portalSide), 1),
            EasyWeightBitMap(intArrayOf(portalSide, asyncronPortal, portalSide), 1)
        )
        private val honeyPuddle = EasyWeightBitMap(
            intArrayOf(
                empty, honey, empty,
                honey, honey, honey,
                empty, honey, empty
            ), 3
        )
        private val oilPuddle = EasyWeightBitMap(
            intArrayOf(
                empty, oil, empty,
                oil, oil, oil,
                empty, oil, empty
            ), 3
        )

    }

    private var _representation = EasyWeightBitMap(
        intArrayOf(
            corner, wall, wall, wall, corner,
            wall, empty, empty, empty, wall,
            wall, empty, empty, empty, wall,
            wall, empty, empty, empty, wall,
            corner, wall, wall, wall, corner
        ), 5
    )

    fun Draw(pic: IntArray, minColumn: Int, minLine: Int, maxColumn: Int, maxLine: Int) {
        for (column in minColumn..maxColumn) {
            for (line in minLine..maxLine) {
                _representation[line * 5 + column] =
                        pic[(line - minLine + 1) * (maxColumn - minColumn + 1) + (column - minColumn + 1)]
            }
        }

    }

    override var representation = _representation
        get() {
            Byte.MAX_VALUE
            synchronized(_representation) {
                synchronized(defRepresentation) {
                    _representation.DrawOn(defRepresentation, Position())
                }
                synchronized(Data.modified) {
                    Data.modified = false
                }


                if (Data.functionality != null)
                    _representation.DrawOn((Data.functionality!!.representation as BLRepresentation).representation,Position(1,1))


                if (Data.friction == Liquid.Honey.friction)
                    _representation.DrawOn(honeyPuddle, Position(1,1))
                else if (Data.friction == Liquid.Oil.friction)
                    _representation.DrawOn(honeyPuddle,Position(1,1))

                if (Data.onThis != null)
                    _representation.DrawOn(((Data.onThis as Moveable).representation as BLRepresentation).representation,Position(1,1))


                //_representation[1][1]=Data.Self?.position?.column.toString()[0]
                //_representation[1][3]=Data.Self?.position?.line.toString()[0]


                if (start) {
                    var d: Direction
                    var selfPosition: Position = Data.Self!!.position
                    var neighbourPosition: Position
                    var index: Int

                    for (i in intArrayOf(0, 4, 20, 24))
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
                                    index = 2
                                else
                                    index = 3
                            } else
                                index = 0
                        } else {
                            index = 1
                        }



                        if ((d.ordinal - 1) % 2 == 0) {
                            _representation.DrawOn(horizontalWalls[index], Position(1,2 + d.vector.line * 2))
                        } else {
                            _representation.DrawOn(verticalWalls[index], Position(2 + d.vector.column * 2,1))
                        }
                    }
                    start = false
                }
                return _representation
            }
        }
}