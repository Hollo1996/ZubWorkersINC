package boss.zubworkersinc.view

import android.content.Context
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import boss.zubworkersinc.basics.LifeCicle
import boss.zubworkersinc.graphics.bitmap.BitLoader
import boss.zubworkersinc.graphics.base.PainterSurface
import boss.zubworkersinc.graphics.bitmap.PainterThread

class MySurface : SurfaceView, PainterSurface {


    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override val holderP=holder
    override val cicle: LifeCicle = object : LifeCicle() {
        val ms: MySurface = this@MySurface
        override fun createInner() {
            justStart = true
            BitLoader.create()
            painter = PainterThread(ms)
        }

        override fun destroyInner() {
            painter = null
            BitLoader.destroy()
        }

        override fun startInner() {
            BitLoader.start()
            if (painter?.isAlive == false)
                painter?.start()
        }

        override fun stopInner() {
            var retryp = true
            while (retryp) {
                try {
                    painter?.join()
                    retryp = false
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                BitLoader.stop()
            }
            System.out.println("Stopped!!")
        }

        override fun pauseInner() {
            BitLoader.pause()
        }

        override fun resumeInner() {
            painter?.Notify("Resume")
            BitLoader.resume()
        }
    }

    init {
        holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                // empty
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                //empty
            }

            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
                //empty
            }
        })

        BitLoader.surface = this
    }

    override var painter: PainterThread? = null
    var justStart = true

    var size: Int = -1


}