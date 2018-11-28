package boss.zubworkersinc.datas.textfile

import java.lang.Exception
import java.util.*

data class Flags(
    var towardsRight: Boolean,
    var towardsLeft: Boolean,
    var givenRight: Boolean,
    var givenLeft: Boolean,
    var directionUP: Boolean,
    var directionRIGHT: Boolean,
    var directionDOWN: Boolean,
    var directionLEFT: Boolean,
    var random: Boolean,
    var immediate: Boolean,
    var optional: Boolean,
    var add: Boolean
) {
    operator fun get (dir: Int):Boolean{
        when(dir){
            0->return directionUP
            1->return directionRIGHT
            2->return directionDOWN
            3->return directionLEFT
            else->throw Exception("Out Of Range! Only 4 directions exists")
        }
    }


    operator fun set (dir: Int,value:Boolean){
        when(dir){
            0-> directionUP=value
            1-> directionRIGHT=value
            2-> directionDOWN=value
            3-> directionLEFT=value
            else->throw Exception("Out Of Range! Only 4 directions exists")
        }
    }

    fun valueOf(flagPart: String) {
        var tmp: Int
        if (flagPart[0] == '-' && flagPart[flagPart.length - 1] == '-') {
            tmp = Random().nextInt(4)
            when (tmp) {
                0 -> {
                    towardsRight = true
                    towardsLeft = false
                }
                1 -> {
                    towardsRight = false
                    towardsLeft = true
                }
                2 -> {
                    towardsRight = true
                    towardsLeft = true
                }
            }
        } else {
            if (flagPart[0] == '<')
                towardsLeft = true
            if (flagPart[flagPart.length - 1] == '>')
                towardsRight = true

        }

        var i = 1
        while (i <= flagPart.length - 3) {
            when (flagPart[i]) {
                'U' -> {
                    if (flagPart[i + 1] == 'P') {
                        directionUP = true
                        i++
                    }
                }
                'R' -> {
                    if (flagPart[i + 1] == 'I') {
                        directionDOWN = true
                        i++
                    } else if (flagPart[i + 1] == 'A') {
                        random = true
                        i++
                    }
                }
                'D' -> {
                    if (flagPart[i + 1] == 'O') {
                        directionRIGHT = true
                        i++
                    }
                }
                'L' -> {
                    if (flagPart[i + 1] == 'E') {
                        directionLEFT = true
                        i++
                    }
                }
                'O' -> {
                    if (flagPart[i + 1] == 'R') {
                        directionUP = true
                        directionDOWN = true
                        i++
                    }
                }
                'H' -> {
                    if (flagPart[i + 1] == 'O') {
                        directionRIGHT = true
                        directionLEFT = true
                        i++
                    }
                }
                'A' -> {
                    if (flagPart[i + 1] == 'L') {
                        directionUP = true
                        directionRIGHT = true
                        directionDOWN = true
                        directionLEFT = true
                        i++
                    } else if (flagPart[i + 1] == 'U') {
                        directionUP = false
                        directionRIGHT = false
                        directionDOWN = false
                        directionLEFT = false
                        i++
                    }
                }
                '#' -> {
                    immediate = true
                }
                '=' -> {
                    immediate = false
                }
                '?' -> {
                    optional = true
                }
                '!' -> {
                    optional = false
                }

                '+' -> {
                    add = true
                }
                '×' -> {
                    add = false
                }
            }
            i++
        }
    }
}