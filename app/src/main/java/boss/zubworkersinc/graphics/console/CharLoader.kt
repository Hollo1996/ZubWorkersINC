package boss.zubworkersinc.graphics.console

import boss.zubworkersinc.basics.BuffererLock
import boss.zubworkersinc.basics.LifeState
import boss.zubworkersinc.basics.Position
import boss.zubworkersinc.graphics.EasyWeightMap
import boss.zubworkersinc.graphics.base.GraphicLoader
import boss.zubworkersinc.graphics.bitmap.BitLoader.justStart
import boss.zubworkersinc.graphics.console.little.CLFieldRepresentation
import boss.zubworkersinc.models.ModelContainer
import kotlin.concurrent.thread


object CharLoader : GraphicLoader<Char>() {
    override var tileSizeInPixel: Position = Position()
        set(value) {
            Picture = EasyWeightMap(
                value.column * pictureSizeInTile.column,
                value.line * pictureSizeInTile.line,
                ' '
            )
            //println("Invalidate SetPictureSize")
            invalidate()
        }
    override var pictureSizeInTile: Position = Position()
        set(value) {
            Picture = EasyWeightMap(
                value.column * tileSizeInPixel.column,
                value.line * tileSizeInPixel.line,
                ' '
            )
            //println("Invalidate SetPictureSize")
            invalidate()
        }
    override lateinit var Picture: EasyWeightMap<Char>

    private var updater: Thread? = null
    private var painter: Thread? = null
    private var updateLock = BuffererLock("Update")
    private var paintLock = BuffererLock("Paint")


    override fun createInner() {
        justStart = true
    }

    override fun destroyInner() {
        painter=null
        updater=null
    }

    override fun startInner() {
        initUpdater()
        initPainter()
    }

    override fun stopInner() {
        var retryu = true
        var retryp = true
        while (retryu || retryp) {
            try {
                updater?.join()
                retryu = false
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            try {
                painter?.join()
                retryp = false
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        System.out.println("Stopped!!")
    }

    override fun pauseInner() {
    }

    override fun resumeInner() {
        updateLock.Notify("CharLoader:Resume")
    }

    private fun initUpdater() {

        updater = thread {

            var changed = false
            synchronized(Picture) {
                for (i in 0..(Picture.columnSize - 1))
                    for (j in 0..(Picture.lineSize - 1))
                        Picture[i, j] = '*'
            }
            //println("Picture is reseted by Update")
            while (state != LifeState.Created) {
                synchronized(Picture)
                {
                    synchronized(ModelContainer)
                    {
                        ////println("\n I jast started Updating!")
                        for (mapCooord in ModelContainer.fieldsMap.keys) {
                            if (ModelContainer.fieldsMap[mapCooord]!!.Modified()) {
                                changed = true
                                Picture.drawOn(
                                    ((ModelContainer.fieldsMap[mapCooord]?.representation) as CLFieldRepresentation).representation,
                                    mapCooord
                                )
                            }
                        }
                        //println("Data Checked")
                    }
                }
                if (changed) {
                    //println("Data Updated by Update")
                    paintLock.Notify("Update")
                    changed = false
                }
                updateLock.Wait()
            }
        }
        updater?.name = "UpdaterThread"

    }

    private fun initPainter() {

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
                    for (i in 0..(Picture.columnSize - 1)) {
                        print("\u001B[${i + 1};0H")
                        for (j in 0..(Picture.lineSize - 1)) {
                            if (!unrelevant && Picture[i, j] == '*') {
                                unrelevant = true
                                continue
                            } else if (Picture[i, j] != '*' && unrelevant) {
                                unrelevant = false
                                print("\u001B[${i + 1};${j + 1}H")
                            }
                            if (unrelevant) {
                                continue
                            } else {
                                print(Picture[i, j])
                                Picture[i, j] = '*'
                            }
                        }

                        if (start)
                            print('\n')
                    }
                    if (start)
                        start = false
                    print("\u001B[${Picture.columnSize + 1};0H")
                    println(x.toString())
                    //println("Paint Ended!")
                }
            }
            //println("Paint Stopped")
        }
        painter?.name = "PainterThread"

    }

    @Synchronized
    override fun invalidate() {
        updateLock.Notify("CharLoader:Invalidate")
    }

}
