package boss.zubworkersinc.basics

data class Position(var column: Int = 0, var line: Int = 0) {

	operator fun get(dimension: Int): Int {
		if (dimension % 2 == 0)
			return column
		else
			return line
	}

	operator fun set(dimension: Int, value: Int) {
		if (dimension % 2 == 0)
			column = value
		else
			line = value
	}

	operator fun plus(p: Position): Position {
		return Position(column + p.column, line + p.line)
	}

	override fun toString(): String {
		return "(" + column + ";" + line + ")"
	}

	companion object {
		fun valueOf(s: String): Position {
			if (!("^\\(\\d{1,};\\d{1,}\\)$".toRegex().matches(s)))
				throw  Exception(s + " is not in the correct coord format")
			val deviderIndex: Int = s.indexOf(';')
			val column:Int = s.substring(1, deviderIndex).toInt()
			val line:Int = s.substring(deviderIndex + 1, s.length-1).toInt()
			return Position(column, line)
		}
	}

}