package boss.zubworkersinc.graphics.bitmap

import android.content.Context
import android.graphics.*
import android.view.SurfaceHolder
import android.view.SurfaceView
import boss.zubworkersinc.basics.BuffererLock
import boss.zubworkersinc.basics.Position
import boss.zubworkersinc.graphics.base.GraphicLoader
import boss.zubworkersinc.graphics.bitmap.little.BLFieldRepresentation
import boss.zubworkersinc.model.ModelContainer
import kotlinx.android.synthetic.main.activity_game_surface.view.*
import kotlin.concurrent.thread

class BitLoader(context: Context) : SurfaceView(context), GraphicLoader {

    var running = false
    var updater: Thread? = null
    var painter: Thread? = null
    var paintLock = BuffererLock("Painter")
    var updateLock = BuffererLock("Update")


    var tileSizeInPixel = Position(5, 5)
    override lateinit var pictureSizeInTile: Position
    var Picture: MutableMap<Position, EasyWeightBitMap> = mutableMapOf<Position, EasyWeightBitMap>()

    var size: Int = -1


    fun pause() {
        surface.
        running = false
        while (true) {
            try {
                painter?.join()
                updater?.join()

            } catch (e: Exception) {
                e.printStackTrace()
            }
            break
        }
        painter = null
        updater = null
    }

    fun resume() {

        synchronized(running) {
            if (running)
                return
            else
                running = true
        }
        start = true
        initUpdater()
        initPainter()
        if (!(updater?.isAlive ?: true) && !(painter?.isAlive ?: true)) {
            updater?.start()
            painter?.start()
        }
    }

    override fun Start() {
        resume()
    }

    var start = true
    var ourRect = Rect()
    var p = Paint()

    fun initUpdater() {

        updater = thread {

            //println("Running is Checked")

            var changed = false
            synchronized(Picture) {
                Picture.clear()
                for (mapCooord in ModelContainer.fieldsMap.keys) {
                    Picture[mapCooord] = EasyWeightBitMap(tileSizeInPixel.column, tileSizeInPixel.line)
                }
            }
            //println("Picture is reseted by Update")
            while (running) {
                synchronized(Picture)
                {
                    synchronized(ModelContainer)
                    {
                        ////println("\n I jast started Updating!")
                        for (mapCooord in ModelContainer.fieldsMap.keys) {
                            if (ModelContainer.fieldsMap[mapCooord]!!.Modified()) {
                                changed = true
                                Picture[mapCooord]?.DrawOn(
                                    ((ModelContainer.fieldsMap[mapCooord]?.representation) as BLFieldRepresentation).representation,
                                    Position()
                                )
                            }
                        }
                        //println("bitLoader Data Checked")
                    }
                }
                if (changed) {
                    //println("bitLoader Data Updated by Update")
                    paintLock.Notify("Update")
                    changed = false
                }
                updateLock.Wait()
            }
            //println("Update Stopped")
        }
        updater?.name = "UpdaterThread"

    }

    fun initPainter() {

        painter = thread {
            var x = 0
            var unrelevant: Boolean
            updateLock.Notify("Paint")
            while (running) {
                paintLock.Wait()
                synchronized(Picture)
                {
                    x++
                    unrelevant = false
                    for (pos in Picture.keys) {
                        val tileRep: EasyWeightBitMap = Picture[pos] as EasyWeightBitMap
                        if (!unrelevant && tileRep[0] == 0x00000000) {
                            unrelevant = true
                            continue
                        } else if (tileRep[0] != 0x00000000 && unrelevant) {
                            unrelevant = false
                        }
                        if (unrelevant) {
                            continue
                        } else {
                            PaintIt(tileRep.makeBitMap(), pos)
                            tileRep[0] = 0x00000000
                        }
                    }
                    println(x.toString())
                    //println("Paint Ended!")
                }
            }
            //println("Paint Stopped")
        }
        painter?.name = "PainterThread"

    }


    private fun PaintIt(bmp: Bitmap, pos: Position) {
        if (!holder.surface.isValid)
            return

        //display image

        var c: Canvas? = null
        try {
            synchronized(holder) {
                c = holder.lockCanvas()
            }
            c!!.drawARGB(255, 0, 0, 0)
            if (start) {
                ourRect.set(0, 0, c!!.width ?: 0, c!!.width ?: 0)
                var p = Paint()
                p.color = Color.BLUE
                p.style = Paint.Style.FILL
                start = false
                c!!.drawRect(ourRect, p)
            }

            //Logic to calculate the Top Left, right bottom corners to divide the tablet screen into   4parts for 4 frames receiving from server
            val height = bmp.getHeight()
            val width = bmp.getWidth()
            val h = height.toFloat()
            val w = width.toFloat()

            //Frame rectangle for each frame
            if (c != null) {
                var mat = Matrix()
                mat.setTranslate(pos.column * w, pos.line * h)
                mat.setScale(1 / w, 1 / h)
                synchronized(holder)
                {
                    c!!.drawBitmap(bmp, mat, Paint())
                }
            }
        } finally {
            if (c != null) {
                synchronized(holder) {
                    holder.unlockCanvasAndPost(c)
                }
            }
        }
    }


    @Synchronized
    override fun Invalidate() {
        updateLock.Notify("Invalidate")
    }

}