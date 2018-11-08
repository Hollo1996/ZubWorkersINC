package boss.zubworkersinc.controls

import android.view.KeyEvent
import boss.zubworkersinc.basics.BuffererLock
import boss.zubworkersinc.console.charLoader
import kotlin.concurrent.thread

object Control {
    //Main data objects
    var escape: Int = KeyEvent.KEYCODE_ESCAPE
    var pressedKeyCode: Int = -1
    var thread = Thread()
    var controlKeySettings = ControlKeySettings()
    var Interface: ControlInterface

    //Sincronizatioon objects
    var loopRunning = 0
    var transferLock = BuffererLock("Transfer")
    var readerLock = BuffererLock("Reader")


    init {
        Interface = ControlInterface("player1", ControlKeySettings().eventKeyMap)
        thread = thread {
            transferKeyLoop()
        }
    }

    //Reads keys from Console, Clears the Console and starts and syncronyses key code transport.
    fun readKeyLoop() {
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
                while (pressedKeyCode==10){
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
                    charLoader.Invalidate()
                    //println("Invalidated")
                }
            }
        }
    }
}
