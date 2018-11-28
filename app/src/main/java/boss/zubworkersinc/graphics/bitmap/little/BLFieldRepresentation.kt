package boss.zubworkersinc.graphics.bitmap.little

import android.graphics.Color
import boss.zubworkersinc.basics.Direction
import boss.zubworkersinc.basics.Position
import boss.zubworkersinc.controls.Liquid
import boss.zubworkersinc.graphics.EasyWeightMap
import boss.zubworkersinc.graphics.base.FieldRepresentation
import boss.zubworkersinc.models.ModelContainer
import boss.zubworkersinc.models.map.Field
import boss.zubworkersinc.models.movables.Movable

class BLFieldRepresentation : FieldRepresentation, BLRepresentation {
    override lateinit var Data: Field.RepresentableData

    private var start = true

    companion object {
        val empty:Int = Color.BLACK
        val portalSide:Int = Color.BLUE or Color.RED
        val syncronPortal:Int = Color.BLUE
        val asyncronPortal:Int = Color.RED
        val wall:Int = Color.GRAY
        val corner:Int = Color.GRAY
        val honey:Int = Color.YELLOW
        val oil:Int = Color.DKGRAY

        private val defRepresentation = EasyWeightMap(
            arrayOf(
                corner, wall, wall, wall, corner,
                wall, empty, empty, empty, wall,
                wall, empty, empty, empty, wall,
                wall, empty, empty, empty, wall,
                corner, wall, wall, wall, corner
            ), 5, 0
        )

        private val horizontalWalls = arrayOf(
            EasyWeightMap(arrayOf(empty, empty, empty), 3, 0),
            EasyWeightMap(arrayOf(wall, wall, wall), 3, 0),
            EasyWeightMap(arrayOf(portalSide, syncronPortal, portalSide), 3, 0),
            EasyWeightMap(arrayOf(portalSide, asyncronPortal, portalSide), 3, 0)
        )
        private val verticalWalls = arrayOf(
            EasyWeightMap(arrayOf(empty, empty, empty), 1, 0),
            EasyWeightMap(arrayOf(wall, wall, wall), 1, 0),
            EasyWeightMap(arrayOf(portalSide, syncronPortal, portalSide), 1, 0),
            EasyWeightMap(arrayOf(portalSide, asyncronPortal, portalSide), 1, 0)
        )
        private val honeyPuddle = EasyWeightMap(
            arrayOf(
                empty, honey, empty,
                honey, empty, honey,
                empty, honey, empty
            ), 3, 0
        )
        private val oilPuddle = EasyWeightMap(
            arrayOf(
                empty, oil, empty,
                oil, empty, oil,
                empty, oil, empty
            ), 3, 0
        )

    }

    private var _representation = EasyWeightMap(
        arrayOf(
            corner, wall, wall, wall, corner,
            wall, empty, empty, empty, wall,
            wall, empty, empty, empty, wall,
            wall, empty, empty, empty, wall,
            corner, wall, wall, wall, corner
        ), 5, 0
    )

    override var representation = _representation
        get() {
            Byte.MAX_VALUE
            synchronized(_representation) {
                synchronized(defRepresentation) {
                    _representation.drawOn(defRepresentation, Position())
                }
                synchronized(Data.modified) {
                    Data.modified = false
                }


                if (Data.functionality != null)
                    _representation.drawOn((Data.functionality!!.representation as BLRepresentation).representation,Position(1,1))


                if (Data.friction == Liquid.Honey.friction)
                    _representation.drawOn(honeyPuddle, Position(1,1))
                else if (Data.friction == Liquid.Oil.friction)
                    _representation.drawOn(oilPuddle,Position(1,1))

                if (Data.onThis != null)
                    _representation.drawOn(((Data.onThis as Movable).representation as BLRepresentation).representation,Position(1,1))


                //_representation[1][1]=Data.Self?.position?.column.toString()[0]
                //_representation[1][3]=Data.Self?.position?.line.toString()[0]


                if (start) {
                    var d: Direction
                    val selfPosition: Position = Data.Self!!.position
                    var neighbourPosition: Position
                    var index: Int

                    for (i in arrayOf(0, 4, 20, 24))
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
                            _representation.drawOn(horizontalWalls[index], Position(1,2 + d.vector.line * 2))
                        } else {
                            _representation.drawOn(verticalWalls[index], Position(2 + d.vector.column * 2,1))
                        }
                    }
                    start = false
                }
                return _representation
            }
        }
}