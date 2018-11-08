package boss.zubworkersinc.model.loader.textfile

import boss.zubworkersinc.basics.Direction
import boss.zubworkersinc.basics.Position
import boss.zubworkersinc.console.charLoader
import boss.zubworkersinc.model.ModelContainer
import boss.zubworkersinc.model.features.Feature
import boss.zubworkersinc.model.map.Field
import boss.zubworkersinc.model.moveables.Box
import boss.zubworkersinc.upperleayer.Game
import java.util.*
import kotlin.math.max

object modelInitializer {
    val boxesMap = mutableMapOf<Position, Box>()
    val spawnPlaces = mutableListOf<Array<Int>>()
    lateinit var coordinates: Position
    var min: Position? = null
    var max: Position? = null
    var start = false
    var paramList = listOf<String>()

    fun Clear() {
        start = true
        max = null
        min = null
        boxesMap.clear()
        spawnPlaces.clear()

    }

    fun loadBoxesToContainer() {

        for (coord in boxesMap.keys) {
            ModelContainer.boxes.add(boxesMap[coord] as Box)
        }
    }

    fun initField(data: String) {
        var directional = data.contains("<=>")
        var connected = data.contains("<>") || directional
        var spectrum = data.contains("--") || connected
        var startCoordinates: Position
        var endCoordinates: Position
        var swap: Int
        if (spectrum) {
            if (connected) {
                startCoordinates = Position.valueOf(data.substring(0, data.indexOf('<')))
                endCoordinates = Position.valueOf(data.substring(data.indexOf('>') + 1, data.length))
            } else {
                startCoordinates = Position.valueOf(data.substring(0, data.indexOf('-')))
                endCoordinates = Position.valueOf(data.substring(data.indexOf('-') + 2, data.length))
            }
            if (startCoordinates.column > endCoordinates.line) {
                swap = startCoordinates.column
                startCoordinates.column = endCoordinates.column
                endCoordinates.column = swap
            }
            if (startCoordinates.line > endCoordinates.line) {
                swap = startCoordinates.line
                startCoordinates.line = endCoordinates.line
                endCoordinates.line = swap
            }
        } else {
            startCoordinates = Position.valueOf(data)
            endCoordinates = Position.valueOf(data)
        }

        for (column in (startCoordinates.column)..(endCoordinates.column)) {
            for (line in (startCoordinates.line)..(endCoordinates.line)) {
                coordinates = Position(column, line)
                var f = Field(coordinates, Game.viewFactory.getFieldRep())
                ModelContainer.fieldsMap[coordinates] = f

                if (connected && column != startCoordinates.column) {
                    f.AddNeighbour(Position(column - 1, line), Direction.LEFT, false)
                    ModelContainer.fieldsMap[Position(column - 1, line)]?.AddNeighbour(
                        f.position,
                        Direction.RIGHT,
                        false
                    )
                }
                if (connected && line != startCoordinates.line) {
                    f.AddNeighbour(Position(column, line - 1), Direction.UP, false)
                    ModelContainer.fieldsMap[Position(column, line - 1)]?.AddNeighbour(
                        f.position,
                        Direction.DOWN,
                        false
                    )
                }
                if (directional && column == endCoordinates.column) {
                    f.AddNeighbour(Position(startCoordinates.column, line), Direction.RIGHT, false)
                    ModelContainer.fieldsMap[Position(startCoordinates.column, line)]?.AddNeighbour(
                        f.position,
                        Direction.LEFT,
                        false
                    )
                }
                if (directional && line == endCoordinates.line) {
                    f.AddNeighbour(Position(column, startCoordinates.line), Direction.DOWN, false)
                    ModelContainer.fieldsMap[Position(column, startCoordinates.line)]?.AddNeighbour(
                        f.position,
                        Direction.UP,
                        false
                    )
                }

            }
        }

        if (start) {
            min = Position(startCoordinates.column, startCoordinates.line)
            max = Position(endCoordinates.column, endCoordinates.line)
            start = false
        } else {
            val nonNullMin: Position = min as Position
            val nonNullMax: Position = max as Position
            if (startCoordinates.column < nonNullMin.column)
                nonNullMin.column = startCoordinates.column
            if (startCoordinates.line < nonNullMin.line)
                nonNullMin.line = startCoordinates.line
            if (endCoordinates.column > nonNullMax.column)
                nonNullMax.column = endCoordinates.column
            if (endCoordinates.line > nonNullMax.line)
                nonNullMax.line = endCoordinates.line
        }
    }

    fun initBox(data: String) {
        coordinates = Position.valueOf(data)
        if (!(ModelContainer.fieldsMap.containsKey(coordinates))) {
            throw Exception("There is no filed to place the box on! The position: " + data)
        }

        boxesMap[coordinates] = Box(Game.viewFactory.getBoxdRep())
        ModelContainer.fieldsMap[coordinates]?.AddMoveable(boxesMap[coordinates] ?: Box(Game.viewFactory.getBoxdRep()))
    }

    fun initSpawn(line: String) {

        //paramList = line.split('_')
        //coordinates = Position.valueOf(paramList[0])
        coordinates = Position.valueOf(line)
        if (!(ModelContainer.fieldsMap.containsKey(coordinates))
        ) {
            throw Exception("Wrong Coords Added at line: " + line)
        }

        var dtmp = arrayOf(coordinates.column, coordinates.line, 5)
        spawnPlaces.add(dtmp)
    }

    fun initBoxContainer(line: String) {
        /*
        coordinates = new Position(inputLine)
        if (!(fieldsMap.ContainsKey(coordinates[x]) && container.fieldsMap[coordinates[x]].ContainsKey(coordinates[y])))
        {
            throw new WrongCoordException("NOPE")
        }

        if (!boxContainerMap.ContainsKey(coordinates[x]))
        {
            boxContainerMap.Add(coordinates[x], new Dictionary<int, BoxContainer>())
        }

        boxContainerMap[coordinates[x]].Add(coordinates[y], new BoxContainer())
        container.fieldsMap[coordinates[x]][coordinates[y]].AddFeature(boxContainerMap[coordinates[x]][coordinates[y]]);*/

    }

    fun initHole(line: String) {
        /*
        coordinates = new Position(inputLine)
        if (!(fieldsMap.ContainsKey(coordinates[x]) && container.fieldsMap[coordinates[x]].ContainsKey(coordinates[y])))
        {
            throw new WrongCoordException("Wrong Coords Added at line: " + inputLine)
        }

        if (!holeMap.ContainsKey(coordinates[x]))
        {
            holeMap.Add(coordinates[x], new Dictionary<int, Hole>())
        }

        holeMap[coordinates[x]].Add(coordinates[y], new Hole())
        container.fieldsMap[coordinates[x]][coordinates[y]].AddFeature(holeMap[coordinates[x]][coordinates[y]]);*/

    }

    fun initButton(line: String) {
        /*
        coordinates = new Position(inputLine)
        if (!(fieldsMap.ContainsKey(coordinates[x]) && container.fieldsMap[coordinates[x]].ContainsKey(coordinates[y])))
        {
            throw new WrongCoordException("NOPE")
        }

        if (!buttonMap.ContainsKey(coordinates[x]))
        {
            buttonMap.Add(coordinates[x], new Dictionary<int, Button>())
        }

        buttonMap[coordinates[x]].Add(coordinates[y], new Button())
        container.fieldsMap[coordinates[x]][coordinates[y]].AddFeature(buttonMap[coordinates[x]][coordinates[y]]);*/
    }

    fun initFallTrap(line: String) {
        /*
        coordinates = new Position(inputLine)
        if (!(fieldsMap.ContainsKey(coordinates[x]) && container.fieldsMap[coordinates[x]].ContainsKey(coordinates[y])))
        {
            throw new WrongCoordException("Wrong Coords Added at line: " + inputLine)
        }

        if (!fallTrapMap.ContainsKey(coordinates[x]))
        {
            fallTrapMap.Add(coordinates[x], new Dictionary<int, FallTrap>())
        }

        fallTrapMap[coordinates[x]].Add(coordinates[y], new FallTrap(fieldsMap[coordinates[x]][coordinates[y]]))
        container.fieldsMap[coordinates[x]][coordinates[y]].AddFeature(fallTrapMap[coordinates[x]][coordinates[y]]);*/
    }




    var direction = mutableListOf<Direction>()
    var left = mutableListOf<Position>()
    var right = mutableListOf<Position>()
    var known: Position = Position(-1, -1)
    var toCalculate: Position = Position()
    var flags = Flags(false, false, false, true, true, false, false, false, false, true, true, true)
    fun initNeighbourhood(data: String) {
        //Clear data
        direction.clear()
        left.clear()
        right.clear()
        //the indexes of the first end last character of a coord
        var coordStart: Int
        var coordEnd: Int = data.indexOf(')')
        //parts of the incoming data
        var rightCoordPart: String
        var typeFlagsPart: String
        var leftCoordPart = data.substring(0, coordEnd + 1)
        var remainingPart = data.substring(coordEnd + 1, data.length)
        //Processing all of the right sides connecting to the left side.
        while (remainingPart != "") {
            //Clear data
            direction.clear()
            right.clear()
            left.clear()
            //calculate the start and the end of the next coord
            coordEnd = remainingPart.indexOf(')')
            coordStart = remainingPart.indexOf('(')
            //calculate the next coordinate in the remaining string and the flags about it's connection tod the left coordinate.
            rightCoordPart = remainingPart.substring(coordStart, coordEnd + 1)
            typeFlagsPart = remainingPart.substring(0, coordStart)
            //cutting down the used data
            remainingPart = remainingPart.substring(coordEnd + 1, remainingPart.length)

            //regenerate flags from string
            flags = Flags(false, false, false, true, false, false, false, false, false, true, true, true)
            flags.valueOf(typeFlagsPart)
            //is the left coordinate given
            if (leftCoordPart.compareTo("(;)") == 0) {
                flags.givenLeft = false
            } else {
                flags.givenLeft = true
                left.add(Position.valueOf(leftCoordPart))
            }
            //is the right coordinate given
            if (rightCoordPart.compareTo("(;)") == 0) {
                flags.givenRight = false
            } else {
                flags.givenRight = true
                right.add(Position.valueOf(rightCoordPart))
            }
            //random irány generálása, ha szükséges
            var tmp: Int
            if (flags.random) {
                tmp = Random().nextInt(16)

                for (i in 0..3) {
                    flags[i] = (tmp % 2 == 1)
                    tmp /= 2
                }
            }
            //Check for semantic error
            if ((!flags.givenLeft && (!flags[0] && !flags[1] && !flags[2] && !flags[3])) ||
                (!flags.givenRight && (!flags[0] && !flags[1] && !flags[2] && !flags[3])) ||
                (!flags.givenLeft && !flags.givenRight)
            )
                throw Exception("Too many data missing at line" + data)
            //Calculate random directions
            if ((!flags[0] && !flags[1] && !flags[2] && !flags[3])) {
                flags[0] = (left[0].line > right[0].line)
                flags[2] = (left[0].line < right[0].line)
                flags[1] = (left[0].column < right[0].column)
                flags[3] = (left[0].column > right[0].column)
            }

            for (i in 0..3) {
                if (flags[i])
                    direction.add(Direction.values()[i + 1])
            }

            if (!flags.givenLeft || !flags.givenRight) {
                if (flags.givenRight)
                    known = right[0]
                else
                    known = left[0]
                if (!known.equals(null) && !(ModelContainer.fieldsMap.containsKey(known)))
                    throw Exception("Wrong Coords Added at line: " + data)

                var Keys = arrayOf(mutableListOf<Int>(), mutableListOf<Int>())
                for (p in ModelContainer.fieldsMap.keys) {
                    if (!Keys[1].contains(p.column))
                        Keys[0].add(p.column)
                    if (p.column == known.column && !Keys[0].contains(p.line))
                        Keys[0].add(p.line)

                }
                
                var otherEnd: Int
                var previous: Int

                for (i in 0..3) {
                    if (!flags[i])
                        continue
                    toCalculate = Position()
                    previous = Keys[i % 2].min()!! * (((i + 3) % 4) / 2) + Keys[i % 2].max()!! * (((i + 1) % 4) / 2)
                    otherEnd = previous
                    for (coord in Keys[i % 2]) {
                        if (i % 2 == 0 || ModelContainer.fieldsMap.containsKey(known)) {
                            if ((coord > otherEnd && i % 3 == 0) ||
                                (coord < otherEnd && i % 3 != 0)
                            )
                                otherEnd = coord

                            if ((coord > previous && coord < known[(i + 1) % 2] && i % 3 == 0) ||
                                (coord < previous && coord > known[(i + 1) % 2] && i % 3 != 0)
                            )
                                previous = coord
                        }
                    }

                    toCalculate[i % 2] = known[i % 2]
                    toCalculate[(i + 1) % 2] = previous

                    if (previous == known[(i + 1) % 2]) {
                        if (!flags.immediate) {
                            toCalculate[(i + 1) % 2] = otherEnd
                            if (!flags.givenRight)
                                right.add(toCalculate)
                            else
                                left.add(toCalculate)
                        } else {
                            continue
                        }
                    } else {
                        if (!flags.givenRight)
                            right.add(toCalculate)
                        else
                            left.add(toCalculate)
                    }

                }


            }
            var index = 0
            var syncronIncrement = max(right.size, left.size) == direction.size
            for (pr in right) {
                for (pl in left) {
                    if (syncronIncrement) {
                        if (flags.add) {
                            if (flags.towardsRight)
                                if (flags.givenLeft)
                                    ModelContainer.fieldsMap[pl]?.AddNeighbour(pr, direction[index], !flags.optional)
                                else
                                    ModelContainer.fieldsMap[pl]?.AddNeighbour(pr, direction[index].GetReverse(), !flags.optional)

                            if (flags.towardsLeft)
                                if (flags.givenLeft)
                                    ModelContainer.fieldsMap[pr]?.AddNeighbour(pl, direction[index].GetReverse(), !flags.optional)
                                else
                                    ModelContainer.fieldsMap[pr]?.AddNeighbour(pl, direction[index], !flags.optional)
                        } else {
                            if (flags.towardsRight)
                                ModelContainer.fieldsMap[pl]?.RemoveNeighbour(direction[index])
                            if (flags.towardsLeft)
                                ModelContainer.fieldsMap[pr]?.RemoveNeighbour(direction[index].GetReverse())
                        }
                        index++
                    } else {
                        for (dr in direction) {
                            if (flags.add) {
                                if (flags.towardsRight)
                                    ModelContainer.fieldsMap[pl]?.AddNeighbour(pr, dr, !flags.optional)
                                if (flags.towardsLeft)
                                    ModelContainer.fieldsMap[pr]?.AddNeighbour(pl, dr.GetReverse(), !flags.optional)
                            } else {
                                if (flags.towardsRight)
                                    ModelContainer.fieldsMap[pl]?.RemoveNeighbour(dr)
                                if (flags.towardsLeft)
                                    ModelContainer.fieldsMap[pr]?.RemoveNeighbour(dr.GetReverse())
                            }
                        }
                    }
                }
            }
        }
    }


    fun initWire(line: String) {
        /*
        coordinateParts = inputLine.Split('-')

        coordinates = new Position(coordinateParts[0]); // parse first part
        if (!(buttonMap.ContainsKey(coordinates[x]) && buttonMap[coordinates[x]].ContainsKey(coordinates[y])))
        {
            throw new WrongCoordException("Wrong Coords Added at line: " + inputLine + ". In state:" + state)
        }

        Button buttontmp = buttonMap[coordinates[x]][coordinates[y]]

        coordinates = new Position(coordinateParts[1]); // parse second part
        if (!(fallTrapMap.ContainsKey(coordinates[x]) && fallTrapMap[coordinates[x]].ContainsKey(coordinates[y])))
        {
            throw new WrongCoordException("Wrong Coords Added at line: " + inputLine + ". In state:" + state)
        }

        FallTrap fallTraptmp = fallTrapMap[coordinates[x]][coordinates[y]]

        buttontmp.AddSwitchable(fallTraptmp);*/
    }

    fun initCompatibility(line: String) {
        /*coordinateParts = inputLine.Split('-')
        coordinates = new Position(coordinateParts[0]); // parse first part
        if (!(fieldsMap.ContainsKey(coordinates[x]) && container.container.fieldsMap[coordinates[x]].ContainsKey(coordinates[y])))
        {
            throw new WrongCoordException("Wrong Coords Added at line: " + inputLine)
        }

        if (!(boxContainerMap.ContainsKey(coordinates[x]) && boxContainerMap[coordinates[x]].ContainsKey(coordinates[y])))
        {
            throw new WrongCoordException("NOPE")
        }

        BoxContainer boxContainertmp = boxContainerMap[coordinates[x]][coordinates[y]]

        coordinates = new Position(coordinateParts[1]); // parse second part

        if (!(fieldsMap.ContainsKey(coordinates[x]) && container.fieldsMap[coordinates[x]].ContainsKey(coordinates[y])))
        {
            throw new WrongCoordException("Wrong Coords Added at line: " + inputLine)
        }

        if (!(boxesMap.ContainsKey(coordinates[x]) && boxesMap[coordinates[x]].ContainsKey(coordinates[y])))
        {
            throw new WrongCoordException("Wrong Coords Added at line: " + inputLine + ". In state:" + state)
        }

        Box boxtmp = boxesMap[coordinates[x]][coordinates[y]]

        boxContainertmp.AddBox(boxtmp);*/
    }

    fun initSize() {
        charLoader.pictureSize = Position((max!!.column - min!!.column + 1) * 5, (max!!.line - min!!.line + 1) * 3)
    }
}