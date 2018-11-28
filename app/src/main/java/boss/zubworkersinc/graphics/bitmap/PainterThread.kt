package boss.zubworkersinc.graphics.bitmap

import android.graphics.*
import boss.zubworkersinc.basics.LifeState
import boss.zubworkersinc.basics.NotifiableThread
import boss.zubworkersinc.graphics.EasyWeightMap
import boss.zubworkersinc.graphics.base.PainterSurface

class PainterThread(private val owner: PainterSurface) : NotifiableThread("Painter") {


    var ourRect = Rect()
    var p = Paint()
    var smaller: Int = 0


    override fun run() {
        super.run()
        var x = 0
        var canvas: Canvas? = null

        BitLoader.updater?.Notify("Paint")
        while (owner.cicle.state != LifeState.Created) {
            bufferLock.Wait()
            synchronized(BitLoader.Picture)
            {
                if (owner.holderP.surface.isValid) {
                    try {
                        synchronized(owner.holderP) {
                            canvas = owner.holderP.lockCanvas()
                            while (canvas == null) {
                                System.out.println("canvas is null")
                                canvas = owner.holderP.lockCanvas()
                            }
                        }
                        if (canvas?.width ?: 0 < canvas?.height ?: 1)
                            smaller = canvas?.width ?: 0
                        else
                            smaller = canvas?.height ?: 0

                        drawBackGround(canvas)

                        x++
                        PaintIt(canvas)
                        println(x.toString())

                    } finally {
                        if (canvas != null) {
                            synchronized(owner.holderP) {
                                owner.holderP.unlockCanvasAndPost(canvas)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun drawBackGround(canvas: Canvas?) {
        canvas?.drawARGB(255, 0, 0, 0)

        ourRect = Rect(0, 0, smaller, smaller)
        p = Paint()
        p.color = Color.BLUE
        p.style = Paint.Style.FILL
        canvas?.drawRect(ourRect, p)
    }

    private fun PaintIt(canvas: Canvas?) {
        val bmp=Bitmap.createBitmap(BitLoader.Picture.columnSize,BitLoader.Picture.lineSize,Bitmap.Config.ARGB_8888)
        BitLoader.Picture.copyToBitmap(bmp)
        val canRect = Rect(0, 0, smaller , smaller )
        canvas?.drawBitmap(bmp, null, canRect, null)

    }


    private fun EasyWeightMap<Int>.copyToBitmap(bmp: Bitmap) {
        for (col in 0..(columnSize - 1))
            for (lin in 0..(lineSize - 1)) {
                bmp.setPixel(col, lin, this[col, lin])
            }
    }
}