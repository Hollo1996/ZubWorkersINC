package boss.zubworkersinc

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_level_chooser.*

class LevelChooserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level_chooser)

        test_level.setOnClickListener {
            val i= Intent(ApplicationContextProvider.context,GameSurfaceActivity::class.java)
            startActivity(i)
        }
    }
}
