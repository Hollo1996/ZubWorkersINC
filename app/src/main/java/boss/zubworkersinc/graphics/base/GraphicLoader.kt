package boss.zubworkersinc.graphics.base

import boss.zubworkersinc.basics.LifeCicle
import boss.zubworkersinc.basics.Position
import boss.zubworkersinc.graphics.EasyWeightMap

abstract class GraphicLoader<T:Any>:LifeCicle() {
    abstract var tileSizeInPixel: Position
    abstract var pictureSizeInTile: Position
    abstract var Picture: EasyWeightMap<T>
    abstract fun invalidate()
}