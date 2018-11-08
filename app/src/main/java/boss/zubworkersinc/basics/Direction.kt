package boss.zubworkersinc.basics

enum class Direction(val vec:Position) {
	NONE(Position(0,0)),UP(Position(0,-1)), RIGHT(Position(1,0)), DOWN(Position(0,1)), LEFT(Position(-1,0));

	val vector:Position=vec

	fun GetReverse(): Direction {
		if (ordinal == 0)
			return NONE
		return values()[((ordinal + 1) % 4)+1]
	}
	final fun valueOfShort(s: String): Direction {
		for (d in Direction.values()){
			if(d.name.startsWith(s.toUpperCase()))
				return d
		}
		return Direction.DOWN
	}
}