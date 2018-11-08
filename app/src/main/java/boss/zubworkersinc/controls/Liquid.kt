package boss.zubworkersinc.controls

enum class Liquid(d: Double) {
	Oil(0.5), Honey(2.0);
	val friction = d
}