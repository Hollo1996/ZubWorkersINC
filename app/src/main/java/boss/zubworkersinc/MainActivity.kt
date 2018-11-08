package boss.zubworkersinc

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NewGame.setOnClickListener {
            val i= Intent(ApplicationContextProvider.context,LevelGroupChooserActivity::class.java)
            startActivity(i)
        }
        LoadGame.setOnClickListener {
            val i= Intent(ApplicationContextProvider.context,GameLoaderActivity::class.java)
            startActivity(i)
        }
        AboutUs.setOnClickListener {
            val i= Intent(ApplicationContextProvider.context,AboutUsActivity::class.java)
            startActivity(i)
        }

    }

    override fun onStart() {
        super.onStart()
    }
}
