package boss.zubworkersinc.graphics.bitmap

import android.graphics.Bitmap
import boss.zubworkersinc.basics.Position

class EasyWeightBitMap(var columnSize: Int, var lineSize: Int) {
    var pixels: IntArray = IntArray(columnSize * lineSize)

    init {
        for (num in 0..(columnSize * lineSize - 1))
            pixels[num] = 0xFF000000.toInt()

    }

    operator fun get(xColumn: Int, yLine: Int): Int = pixels[(yLine % lineSize) * columnSize + (xColumn % columnSize)]
    operator fun set(xColumn: Int, yLine: Int, value: Int) {
        pixels[(yLine % lineSize) * columnSize + (xColumn % columnSize)] = value
    }

    operator fun get(index: Int): Int = pixels[index % (columnSize * lineSize)]
    operator fun set(index: Int, value: Int) {
        pixels[index % (columnSize * lineSize)] = value
    }

    constructor(_pixels: IntArray, columnSize: Int) : this(columnSize, _pixels.size / columnSize) {
        for (x in 0..(columnSize - 1))
            for (y in 0..(lineSize - 1))
                pixels[y * columnSize + x] = _pixels[y * columnSize + x]
    }

    fun makeBitMap(): Bitmap {
        return Bitmap.createBitmap(pixels, columnSize, lineSize, Bitmap.Config.ARGB_8888)
    }

    fun DrawOn(other: EasyWeightBitMap, startCorner: Position) {
        var A: Int
        for (x in 0..(other.columnSize - 1))
            for (y in 0..(other.lineSize - 1)) {
                A = other[x, y].getA()
                this[x + startCorner.column, y + startCorner.line] =
                        this[x + startCorner.column, y + startCorner.line].plusColor(other[x, y])
            }


    }

    fun Int.getRGB(): Int = (this and 0x00FFFFFF)

    fun Int.getA(): Int = (this and 0xFF000000.toInt())

    fun Int.getR(): Int = (this and 0x00FF0000)

    fun Int.getG(): Int = (this and 0x0000FF00)

    fun Int.getB(): Int = (this and 0x000000FF)

    fun Int.setRGB(RGB: Int): Int = this.getA() + RGB.getRGB()

    fun Int.setA(A: Int): Int = this.getRGB() + A.getA()

    fun Int.setR(R: Int): Int = (this and 0xFF00FFFF.toInt()) + R.getR()

    fun Int.setG(G: Int): Int = (this and 0xFFFF00FF.toInt()) + G.getG()

    fun Int.setB(B: Int): Int = (this and 0xFFFFFF00.toInt()) + B.getB()

    fun Int.multiplyA(A: Int): Int {
        val multiplyer=A.getA().shr(24) / 0xFF.toDouble()
        var tmp=this
        tmp=tmp.setR((this.getR()*multiplyer).toInt())
        tmp=tmp.setG((this.getR()*multiplyer).toInt())
        tmp=tmp.setB((this.getR()*multiplyer).toInt())
        return tmp
    }

    fun Int.plusColor(other: Int): Int =
        this.setRGB(this.multiplyA(0xFF000000.toInt() - other.getA()) + other.multiplyA(other.getA()))
}