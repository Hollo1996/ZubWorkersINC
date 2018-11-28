package boss.zubworkersinc.graphics.bitmap



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
    val multiplyer = A.getA().shr(24) / 0xFF.toDouble()
    var tmp = this
    tmp = tmp.setR((this.getR() * multiplyer).toInt())
    tmp = tmp.setG((this.getR() * multiplyer).toInt())
    tmp = tmp.setB((this.getR() * multiplyer).toInt())
    return tmp
}

fun Int.plusColor(other: Int): Int =
    this.setRGB(this.multiplyA(0xFF000000.toInt() - other.getA()) + other.multiplyA(other.getA()))