package boss.zubworkersinc.graphics.bitmap

import boss.zubworkersinc.basics.Position
import boss.zubworkersinc.graphics.EasyWeightMap
import boss.zubworkersinc.graphics.base.GraphicLoader
import boss.zubworkersinc.graphics.base.PainterSurface
import java.lang.Exception

object BitLoader : GraphicLoader<Int>(){

    lateinit var surface: PainterSurface
    var updater: UpdaterThread? = null
    var justStart = true


    override var tileSizeInPixel = Position(5, 5)
        set(value) {
            field=value
            Picture = EasyWeightMap(
                pictureSizeInTile.column * tileSizeInPixel.column,
                pictureSizeInTile.line * tileSizeInPixel.line,
                0
            )
        }
    override var pictureSizeInTile: Position = Position(4,4)
        set(value) {
            field=value
            Picture = EasyWeightMap(
                pictureSizeInTile.column * tileSizeInPixel.column,
                pictureSizeInTile.line * tileSizeInPixel.line,
                0
            )
        }
    override var Picture: EasyWeightMap<Int> = EasyWeightMap(
        pictureSizeInTile.column * tileSizeInPixel.column,
        pictureSizeInTile.line * tileSizeInPixel.line,
        0
    )


    var size: Int = -1

    override fun createInner() {
        justStart = true
        updater = UpdaterThread()
    }

    override fun destroyInner() {
        updater = null
    }

    override fun startInner() {
        try {
            if (updater?.isAlive == false)
                updater?.start()
        }catch (e:Exception){
            destroyInner()
            createInner()
            startInner()
        }
    }

    override fun stopInner() {
        var retryu = true
        while (retryu) {
            try {
                updater?.join()
                retryu = false
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        System.out.println("Stopped!!")
    }

    override fun pauseInner() {
    }

    override fun resumeInner() {
        updater?.Notify("BitLoader:Resume")
    }


    @Synchronized
    override fun invalidate() {
        updater?.Notify("BitLoader:Invalidate")
    }

}