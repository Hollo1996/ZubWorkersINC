package boss.zubworkersinc.basics

open class NotifiableThread(name: String) : Thread(name+ idGen.toString()) {
    companion object {
        var idGen=0
    }
    init {
        idGen+=1
    }
    protected var bufferLock: BuffererLock = BuffererLock(name)
    open fun Notify(name: String) {
        bufferLock.Notify(name)
    }
}