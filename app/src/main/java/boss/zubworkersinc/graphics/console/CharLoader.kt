package boss.zubworkersinc.graphics.console

import boss.zubworkersinc.basics.BuffererLock
import boss.zubworkersinc.basics.Position
import boss.zubworkersinc.graphics.base.GraphicLoader
import boss.zubworkersinc.graphics.console.little.CLFieldRepresentation
import boss.zubworkersinc.model.ModelContainer
import kotlin.concurrent.thread


object CharLoader : GraphicLoader {
    var running = 0
    lateinit var Picture: Array<CharArray>
    override var pictureSizeInTile: Position =Position()
        set(value) {
            Picture = Array(value.line, { CharArray(value.column) })
            //println("Invalidate SetPictureSize")
            Invalidate()
        }

    var updater: Thread? = null
    var painter: Thread? = null
    var updateLock = BuffererLock("Update")
    var paintLock = BuffererLock("Paint")


    override fun Start() {
        if (updater == null)
            initUpdater()
        else
            synchronized(updater as Any) {
                updater?.start()
            }
        if (painter == null)
            initPainter()
        else
            synchronized(painter as Any) {
                painter?.start();//
            }
    }

    fun initUpdater() {

        updater = thread {
            var r = 1
            //println("Running is going to be Checked")
            synchronized(running) {
                r = running
            }
            if (r == 0) {
                synchronized(running) {
                    running = 1
                }

                //println("Running is Checked")

                var changed = false
                synchronized(Picture) {
                    for (i in 0..(Picture.size - 1))
                        for (j in 0..(Picture[0].size - 1))
                            Picture[i][j] = '*'
                }
                //println("Picture is reseted by Update")
                while (true) {
                    synchronized(Picture)
                    {
                        synchronized(ModelContainer)
                        {
                            ////println("\n I jast started Updating!")
                            for (mapCooord in ModelContainer.fieldsMap.keys) {
                                if (ModelContainer.fieldsMap[mapCooord]!!.Modified()) {
                                    changed = true
                                    drawOnPicture(
                                        ((ModelContainer.fieldsMap[mapCooord]?.representation) as CLFieldRepresentation).representation,
                                        mapCooord.line * 3,
                                        mapCooord.column * 5
                                    )
                                }
                            }
                            //println("bitLoader Data Checked")
                        }
                    }
                    if (changed) {
                        //println("bitLoader Data Updated by Update")
                        paintLock.Notify("Update")
                        changed = false
                    }
                    updateLock.Wait()
                }
            }
            //println("Update Stopped")
        }
        updater?.name = "UpdaterThread"

    }

    fun initPainter() {

        painter = thread {
            var x = 0
            var unrelevant: Boolean
            var start = true
            updateLock.Notify("Paint")
            while (true) {
                paintLock.Wait()
                synchronized(Picture)
                {
                    x++
                    //println("\n I jast started Painting!")
                    print("\u001B[1;0H")
                    unrelevant = false
                    if (start)
                        print("\u001Bc") // clear screen first
                    for (i in 0..(Picture.size - 1)) {
                        print("\u001B[${i + 1};0H")
                        for (j in 0..(Picture[0].size - 1)) {
                            if (!unrelevant && Picture[i][j] == '*') {
                                unrelevant = true
                                continue
                            } else if (Picture[i][j] != '*' && unrelevant) {
                                unrelevant = false
                                print("\u001B[${i + 1};${j + 1}H")
                            }
                            if (unrelevant) {
                                continue
                            } else {
                                print(Picture[i][j])
                                Picture[i][j] = '*'
                            }
                        }

                        if (start)
                            print('\n')
                    }
                    if (start)
                        start = false
                    print("\u001B[${Picture.size + 1};0H")
                    println(x.toString())
                    //println("Paint Ended!")
                }
            }
            //println("Paint Stopped")
        }
        painter?.name = "PainterThread"

    }


    fun Stop() {
        updater?.join()
        painter?.join()
    }

    fun drawOnPicture(drawing: Array<Array<Char>>?, lineCoord: Int, columnCoord: Int) {
        if (drawing == null) return
        if (((drawing.size + lineCoord) > Picture.size) ||
            ((drawing[0].size + columnCoord) > Picture[0].size)
        )
            return
        for (line in 0..(drawing.size - 1)) {

            for (column in 0..(drawing[0].size - 1)) {
                Picture[lineCoord + line][columnCoord + column] = drawing[line][column]
            }
        }
    }

    @Synchronized
    override fun Invalidate() {
        updateLock.Notify("Invalidate")
    }

}
