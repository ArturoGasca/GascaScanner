package gasca.com.bardcodescanner.Api

import android.preference.PreferenceManager
import android.support.v4.content.ContextCompat
import android.util.Patterns
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import com.google.gson.GsonBuilder
import gasca.com.bardcodescanner.Activities.SettingsActivity
import gasca.com.bardcodescanner.Application.App
import gasca.com.bardcodescanner.Extra.Delegates
import gasca.com.bardcodescanner.R
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory


/**
 * Created by in-qu on 26/10/2017.
 */
object RetrofitService {

    var instance by Delegates.lazyComponent<Api>{
        val context = App.appContext
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val baseUrl = sharedPref.getString(SettingsActivity.KEY_PREF_SERVER, context!!.getString(R.string.pref_default_server))
        createWithBaseUrl(baseUrl)!!
    }

    fun changeBaseUrl(newBaseUrl: String){
        instance = createWithBaseUrl(newBaseUrl)!!
    }

    private fun createWithBaseUrl(newBaseUrl: String): Api?{
        val url = "http://$newBaseUrl/"
        val gson = GsonBuilder()
                    .setDateFormat("YYYY-MM-dd")
                    .create()

        return Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .build()
                .create(Api::class.java)

    }
}