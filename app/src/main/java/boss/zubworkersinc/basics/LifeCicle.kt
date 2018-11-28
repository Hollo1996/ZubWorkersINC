package boss.zubworkersinc.basics

import boss.zubworkersinc.graphics.bitmap.BitLoader

abstract class LifeCicle {
    var state: LifeState = LifeState.Destroyed

    fun create() {
        synchronized(state) {
            if (state != LifeState.Destroyed)
                return
            else
                state = LifeState.Created
        }
        createInner()
    }

    fun destroy() {
        synchronized(state) {
            if (state != LifeState.Created)
                return
            else
                state = LifeState.Destroyed
        }
        destroyInner()
    }

    fun start() {
        synchronized(state) {
            create()
            if (state != LifeState.Created)
                return
            else
                state = LifeState.Started
        }
        startInner()
    }

    fun stop() {
        synchronized(state) {
            if (state != LifeState.Started)
                return
            else
                state = LifeState.Created
        }
        stopInner()
    }

    fun resume() {
        synchronized(state) {
            if (state != LifeState.Started)
                return
            else
                state = LifeState.Resumed
        }
        resumeInner()
    }

    fun pause() {
        synchronized(state) {
            if (state != LifeState.Resumed)
                return
            else
                state = LifeState.Started
        }
        pauseInner()
    }

    protected abstract fun createInner()
    protected abstract fun destroyInner()
    protected abstract fun startInner()
    protected abstract fun stopInner()
    protected abstract fun resumeInner()
    protected abstract fun pauseInner()

}