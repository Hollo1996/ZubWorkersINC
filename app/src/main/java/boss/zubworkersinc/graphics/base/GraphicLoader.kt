package boss.zubworkersinc.graphics.base

import boss.zubworkersinc.basics.Position

interface GraphicLoader {
    var pictureSizeInTile: Position
    fun Start()
    fun Invalidate()
}