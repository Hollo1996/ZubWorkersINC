package boss.zubworkersinc.controls.console

import android.view.KeyEvent
import boss.zubworkersinc.basics.BuffererLock
import boss.zubworkersinc.basics.LifeState
import boss.zubworkersinc.controls.ControlInterface
import boss.zubworkersinc.controls.ControlKeySettings
import boss.zubworkersinc.controls.base.Control
import boss.zubworkersinc.graphics.console.CharLoader
import kotlin.concurrent.thread

object CControl : Control() {
    /*
    //Reads Commands from the standard input
    fun CommandLoop() {
        var command: String = ""
        while ("Close".compareTo(command) != 0) {
            if ("".compareTo(command) != 0)
                commandHandler(command)
            command = readLine() ?: ""
        }
    }

    //Handles all command loaded from TXT
    fun commandHandler(command: String): Boolean {
        val commandAndParams: List<String> = command.split(' ')
        when (commandAndParams[0]) {
            "Start:" -> start()
            "Stop:" -> stop()
            "Load:" -> Load()
            "Save:" -> Save()
            else -> return false
        }
        return true
    }
    */
    override fun createInner() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun destroyInner() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun startInner() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun stopInner() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun resumeInner() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun pauseInner() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override var Interface: ControlInterface = ControlInterface(
        "player1",
        ControlKeySettings().eventKeyMap
    )
    var escape: Int = KeyEvent.KEYCODE_ESCAPE
    var pressedKeyCode: Int = -1
    var thread = Thread()

    //Sincronizatioon objects
    var loopRunning = 0
    var transferLock = BuffererLock("Transfer")
    var readerLock = BuffererLock("Reader")


    init {
        thread = thread {
            transferKeyLoop()
        }
    }

    //Reads keys from Console, Clears the Console and starts and syncronyses key code transport.
    override fun readKeyLoop() {
        //println("loopRunning is going to be Checked")
        synchronized(loopRunning) {
            if (loopRunning == 1)
                return
            else
                loopRunning = 1
            //println("loopRunning is Checked")
        }

        while (pressedKeyCode != escape) {
            synchronized(pressedKeyCode) {
                //println("pressedKeyCode locked")
                // BufferedReader buffer = new BufferedReader(
                //new InputStreamReader(System.in))
                //var buffer= BufferedReader(InputStreamReader(System.`in`))
                //pressedKeyCode = buffer.read()
                //class Keyboard extends KeyAdapter{
                //
                //      public void keyPressed(KeyEvent ke){
                //       Character text = new Character(ke.getKeyChar())
                //       jta.append(text.toString())
                //     }
                pressedKeyCode = System.`in`.read()
                //println("Read:"+pressedKeyCode.toString())
                while (pressedKeyCode == 10) {
                    pressedKeyCode = System.`in`.read()
                    //println("ReRead:"+pressedKeyCode.toString())
                }
                transferLock.Notify("Reader")
            }
            readerLock.Wait()
        }

        //println("Reader is Stopped")
        synchronized(loopRunning) {
            loopRunning = 0
        }
    }

    fun transferKeyLoop() {
        var keyCode = -1

        while (true) {

            transferLock.Wait()
            synchronized(pressedKeyCode) {
                keyCode = pressedKeyCode
            }
            readerLock.Notify("Transfer")
            //println(keyCode.toString())
            //println(keyCode.toChar())
            if (keyCode != 10) {

                if (pressedKeyCode == escape) {
                    //println("escape")
                    break
                } else {
                    //println("not escape")
                }

                if (Interface.keyHandler(this, keyCode)) {
                    CharLoader.invalidate()
                    //println("Invalidated")
                }
            }
        }
    }
}
