package boss.zubworkersinc.model.loader.textfile

import android.app.Application
import android.content.Context
import boss.zubworkersinc.ApplicationContextProvider
import boss.zubworkersinc.R
import boss.zubworkersinc.model.ModelContainer
import java.io.File

object textLoader {
    lateinit var state: String

    fun LoadMap(filename: String) {
        // Read the file and display it line by line.val input.bufferedReader().use { it.readText() }
         ApplicationContextProvider.context?.resources?.openRawResource(R.raw.testmap_moveable03)?.bufferedReader().use {
            ModelContainer.fieldsMap.clear()
            ModelContainer.boxes.clear()
            ModelContainer.workers.clear()
            state = "none"
            modelInitializer.Clear()

            for (line in it!!.readText().lines()) {

                var inputLine: String
                var datas: List<String>
                var tmp = line.toMutableList()

                tmp.removeAll(arrayOf(' ', '\t', '\n'))
                inputLine = String(tmp.toCharArray()).toUpperCase()
                if (inputLine.startsWith("#") || inputLine.compareTo("") == 0)
                    continue

                if (inputLine.contains(":")) {
                    state = inputLine.substring(0, inputLine.indexOf(':') + 1)
                    if (inputLine.endsWith(":"))
                        continue
                    inputLine = inputLine.substring(inputLine.indexOf(':') + 1, inputLine.length)
                }

                datas = inputLine.split(',')
                for (i in 0..(datas.size - 1))
                    dataProcessor(datas[i])
                modelInitializer.loadBoxesToContainer()
            }
        }
        modelInitializer.initSize()
    }

    fun dataProcessor(data: String) {
        when (state) {
            "FIELD:" -> modelInitializer.initField(data)
            "BOX:" -> modelInitializer.initBox(data)
            "BOXCONATAINER:" -> modelInitializer.initBoxContainer(data)
            "BUTTON:" -> modelInitializer.initButton(data)
            "FALLTRAP:" -> modelInitializer.initFallTrap(data)
            "HOLE:" -> modelInitializer.initHole(data)
            "NEIGHBOURHOOD:" -> modelInitializer.initNeighbourhood(data)
            "NEIGHBORHOOD:" -> modelInitializer.initNeighbourhood(data)
            "WIRE:" -> modelInitializer.initWire(data)
            "COMPATIBILITY:" -> modelInitializer.initCompatibility(data)
            "SPAWN:" -> modelInitializer.initSpawn(data)
            else -> throw Error("Wrong Status header before line:" + data)
        }
    }
}