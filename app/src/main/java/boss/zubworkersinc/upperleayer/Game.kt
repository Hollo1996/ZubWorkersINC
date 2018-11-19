package boss.zubworkersinc.upperleayer

import boss.zubworkersinc.basics.Position
import boss.zubworkersinc.controls.Control
import boss.zubworkersinc.graphics.base.DataRepresentationFactory
import boss.zubworkersinc.graphics.base.GraphicLoader
import boss.zubworkersinc.graphics.bitmap.BitmapDataRepresentationFactory
import boss.zubworkersinc.model.ModelContainer
import boss.zubworkersinc.model.loader.textfile.modelInitializer
import boss.zubworkersinc.model.loader.textfile.textLoader
import boss.zubworkersinc.model.moveables.Worker
import java.io.File

object Game {
    val viewFactory: DataRepresentationFactory = BitmapDataRepresentationFactory()
    val mapLoader =textLoader
    var graphicLoader:GraphicLoader?= null
    val controlLoader = Control
    var state=GameState.Inicialization

    //Loads Command from a txt
    fun LoadCommand(nameOfTxt: String) = File(nameOfTxt+".txt").useLines { lines -> lines.forEach { CommandHandler(it) } }

    //Reads Commands from the standard input
    fun CommandLoop() {
        var command: String = ""
        while ("Close".compareTo(command) != 0) {
            if ("".compareTo(command) != 0)
                CommandHandler(command)
            command = readLine() ?: ""
        }
    }

    //Handles all command loaded from TXT
    fun CommandHandler(command: String): Boolean {
        val commandAndParams: List<String> = command.split(' ')
        when (commandAndParams[0]) {
            "Start:" -> Start()
            "Stop:" -> Stop()
            "Load:" -> Load()
            "Save:" -> Save()
            else -> return false
        }
        return true
    }

    //Starts a whole new game
    fun Start(): Boolean {
        if (state==GameState.InGame)
            return false

        mapLoader.LoadMap("testmap_moveable03")
        controlLoader.Interface.currentWorker= Worker(modelInitializer.spawnPlaces[0][2].toDouble(), viewFactory.getWorkerdRep())
        ModelContainer.fieldsMap[Position(modelInitializer.spawnPlaces[0][0],modelInitializer.spawnPlaces[0][1])]?.AddMoveable(controlLoader.Interface.currentWorker as Worker)

        graphicLoader?.Start()
        controlLoader.readKeyLoop()

        state = GameState.InGame

        return true
    }

    //Stops the current game
    fun Stop(): Boolean {
        return state==GameState.InGame
        //TODO
    }

    //Loads Saved }
    fun Load(): Boolean {
        return state==GameState.Inicialization
        //TODO
    }

    //Saves current Game
    fun Save(): Boolean {
        return state==GameState.InGame
        //TODO
    }


}