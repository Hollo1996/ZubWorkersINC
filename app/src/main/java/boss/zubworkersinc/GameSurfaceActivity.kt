package boss.zubworkersinc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.SurfaceHolder
import android.view.Window
import android.view.WindowManager
import boss.zubworkersinc.graphics.base.WorkerRepresentation
import boss.zubworkersinc.graphics.bitmap.BitLoader
import boss.zubworkersinc.model.ModelContainer
import boss.zubworkersinc.upperleayer.Game
import boss.zubworkersinc.upperleayer.GameState
import boss.zubworkersinc.view.WorkerListAdapter
import kotlinx.android.synthetic.main.activity_game_surface.*

class GameSurfaceActivity : AppCompatActivity(),SurfaceHolder.Callback2 {
    override fun surfaceRedrawNeeded(holder: SurfaceHolder?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    lateinit var view:BitLoader
    lateinit var holder:SurfaceHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        view=BitLoader(this)
        holder=view.holder
        holder.addCallback(this)
        Game.graphicLoader=view
        setContentView(view)


    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {

        super.onResume()
        if(Game.state!=GameState.InGame)
            Game.Start()
        view.resume()
    }

    override fun onPause() {
        super.onPause()
        view.pause()
    }
}
