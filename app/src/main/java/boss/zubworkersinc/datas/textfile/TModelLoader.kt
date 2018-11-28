package boss.zubworkersinc.datas.textfile

import boss.zubworkersinc.ApplicationContextProvider
import boss.zubworkersinc.R
import boss.zubworkersinc.models.ModelContainer
import boss.zubworkersinc.datas.base.ModelLoader

object TModelLoader :ModelLoader{
    lateinit var state: String

    override fun loadMap(filename: String) {
        // Read the file and display it line by line.val input.bufferedReader().use { it.readText() }
         ApplicationContextProvider.context?.resources?.openRawResource(R.raw.testmap_moveable03)?.bufferedReader().use {
            ModelContainer.fieldsMap.clear()
            ModelContainer.boxes.clear()
            ModelContainer.workers.clear()
            state = "none"
            TModelInitializer.clear()

            for (line in it!!.readText().lines()) {

                var inputLine: String
                var datas: List<String>
                val tmp = line.toMutableList()

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
                TModelInitializer.loadBoxesToModelContainer()
            }
        }
        TModelInitializer.initSize()
    }

    fun dataProcessor(data: String) {
        when (state) {
            "FIELD:" -> TModelInitializer.initField(data)
            "BOX:" -> TModelInitializer.initBox(data)
            "BOXCONATAINER:" -> TModelInitializer.initBoxContainer(data)
            "BUTTON:" -> TModelInitializer.initButton(data)
            "FALLTRAP:" -> TModelInitializer.initFallTrap(data)
            "HOLE:" -> TModelInitializer.initHole(data)
            "NEIGHBOURHOOD:" -> TModelInitializer.initNeighbourhood(data)
            "NEIGHBORHOOD:" -> TModelInitializer.initNeighbourhood(data)
            "WIRE:" -> TModelInitializer.initWire(data)
            "COMPATIBILITY:" -> TModelInitializer.initCompatibility(data)
            "SPAWN:" -> TModelInitializer.initSpawn(data)
            else -> throw Error("Wrong Status header before line:" + data)
        }
    }
}