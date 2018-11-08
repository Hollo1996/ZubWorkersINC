package boss.zubworkersinc

import android.app.Application
import android.content.Context


class ApplicationContextProvider : Application() {
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object {
        /**
         * Keeps a reference of the application context
         */
        /**
         * Returns the application context
         *
         * @return application context
         */
        var context: Context? = null
            private set
    }
}