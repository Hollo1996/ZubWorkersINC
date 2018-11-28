package boss.zubworkersinc.upperleayer

import boss.zubworkersinc.basics.LifeCicle
import boss.zubworkersinc.basics.LifeState
import boss.zubworkersinc.basics.Position
import boss.zubworkersinc.controls.base.ControlFactory
import boss.zubworkersinc.controls.key.KControlFactory
import boss.zubworkersinc.graphics.base.GraphicFactory
import boss.zubworkersinc.graphics.bitmap.little.BLGraphicFactory
import boss.zubworkersinc.models.ModelContainer
import boss.zubworkersinc.datas.base.ModelFactory
import boss.zubworkersinc.datas.textfile.TModelFactory
import boss.zubworkersinc.datas.textfile.TModelInitializer
import boss.zubworkersinc.models.movables.Worker
import java.io.File

object Game : LifeCicle() {
    var graphicFactory: GraphicFactory<Any>? = null
    var modelFactory: ModelFactory? = null
    var controlFactory: ControlFactory? = null


    override fun createInner() {
        synchronized(state) {
            if (state != LifeState.Destroyed)
                return
            else
                state = LifeState.Created
        }


        @Suppress("UNCHECKED_CAST")
        graphicFactory = graphicFactory ?: BLGraphicFactory as GraphicFactory<Any>
        modelFactory = modelFactory ?: TModelFactory
        controlFactory = controlFactory ?: KControlFactory
    }

    override fun destroyInner() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun startInner() {
        if (state != LifeState.Created)
            return

        modelFactory?.getModelLoader()?.loadMap("testmap_moveable03")
        controlFactory?.getControl()?.Interface?.currentWorker =
                Worker(TModelInitializer.spawnPlaces[0][2].toDouble(), graphicFactory!!.getWorkerRep())
        ModelContainer.fieldsMap[Position(
            TModelInitializer.spawnPlaces[0][0],
            TModelInitializer.spawnPlaces[0][1]
        )]?.AddMoveable(controlFactory?.getControl()?.Interface?.currentWorker as Worker)
        graphicFactory?.getGraphicLoader()?.start()
        controlFactory?.getControl()?.readKeyLoop()

        state = LifeState.Started
    }

    override fun stopInner() {
        if (state != LifeState.Started)
            return

        state = LifeState.Created
    }

    override fun pauseInner() {
        if (state != LifeState.Started)
            return

        state = LifeState.Created
    }

    override fun resumeInner() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    //Loads Saved }
    fun Load() {
        if (state != LifeState.Started)
            return

        state = LifeState.Created
    }

    //Saves current Game
    fun Save() {
        if (state != LifeState.Started)
            return

        state = LifeState.Created
    }


}