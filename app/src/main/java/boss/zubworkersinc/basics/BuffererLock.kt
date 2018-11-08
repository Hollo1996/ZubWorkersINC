package boss.zubworkersinc.basics

class BuffererLock(var Name: String, var Silent: Boolean = true) {
    private var lockable = Object()
    private var waitSync = Object()
    private var notifySync = Object()
    private var notified = false
    private var awake = true

    fun printOut(s: String) {
        if (!Silent) {
            println(s)
        }
    }

    fun Wait() {
        synchronized(waitSync) {
            printOut(Name + " is going to Wait")
            var notifiedClone: Boolean = false
            synchronized(notified) {
                notifiedClone = notified
                if (notified) {
                    notified = false
                    printOut(Name + " didnt need to Wait")
                }
            }
            if (!notifiedClone) {
                synchronized(awake) {
                    awake = false
                }
                synchronized(lockable) {
                    printOut(Name + "'s Waiting Locked")
                    lockable.wait()
                }
                synchronized(awake) {
                    awake = true
                }
                printOut(Name + " is Notified")
            }
        }
    }

    fun Notify(nameOfNotifier: String) {
        synchronized(notifySync) {
            printOut(Name + " is going to be Notified by " + nameOfNotifier)

            synchronized(notified) {
                printOut(Name + "'s notified is locked by " + nameOfNotifier)
                if (!notified) {
                    synchronized(awake) {
                        printOut(Name + "'s awake is locked by " + nameOfNotifier)
                        if (awake) {
                            notified = true
                            printOut(Name + " is Virtually Notified by " + nameOfNotifier)
                        } else {
                            synchronized(lockable) {
                                printOut(Name + "'s Notification Locked by " + nameOfNotifier)
                                lockable.notify()
                            }
                            printOut(Name + " is Notified by " + nameOfNotifier)
                        }
                    }
                } else {
                    printOut(Name + " didnt need Notification by " + nameOfNotifier)
                }
            }
        }

    }
}