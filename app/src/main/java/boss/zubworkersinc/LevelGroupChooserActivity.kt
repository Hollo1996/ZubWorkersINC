package boss.zubworkersinc

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_level_group_chooser.*

class LevelGroupChooserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level_group_chooser)

        test_levels.setOnClickListener {
            val i= Intent(ApplicationContextProvider.context,LevelChooserActivity::class.java)
            startActivity(i)
        }
    }
}
