package gasca.com.bardcodescanner.Application

import android.app.Application
import android.content.Context

/**
 * Created by in-qu on 29/10/2017.
 */
class App: Application() {

    override fun onCreate() {
        appContext = applicationContext
        super.onCreate()

    }

    companion object {
        var appContext: Context? = null
    }
}