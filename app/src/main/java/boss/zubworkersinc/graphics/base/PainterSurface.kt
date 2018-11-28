package boss.zubworkersinc.graphics.base

import android.view.SurfaceHolder
import boss.zubworkersinc.basics.LifeCicle
import boss.zubworkersinc.graphics.bitmap.PainterThread

interface PainterSurface {
    val painter: PainterThread?
    val cicle:LifeCicle
    val holderP:SurfaceHolder

}