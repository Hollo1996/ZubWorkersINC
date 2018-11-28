package boss.zubworkersinc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import boss.zubworkersinc.view.MySurface

class GameSurfaceActivity : AppCompatActivity(){

    var view:MySurface?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_game_surface)
        //view=surfaceBL
        //BLGraphicFactory.loader=view
    }

    override fun onStart() {
        super.onStart()
        //if(Game.state!=GameState.InGame)
        // Game.Start()
    }

    override fun onStop() {
        view?.cicle?.stop()
        super.onStop()
    }

    override fun onResume() {

        super.onResume()
        view?.cicle?.resume()
    }

    override fun onPause() {
        super.onPause()
        view?.cicle?.pause()
    }
}
