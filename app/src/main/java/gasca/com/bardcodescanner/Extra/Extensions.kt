package gasca.com.bardcodescanner.Extra

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by in-qu on 26/10/2017.
 */
object Extensions {
    fun View.hide(){
        this.visibility = View.INVISIBLE
    }

    fun View.disappear(){
        this.visibility = View.GONE
    }

    fun View.appear(){
        this.visibility = View.VISIBLE
    }

    fun Date.format(format: String, locale: Locale = Locale("es", "MX")): String{
        val sdf = SimpleDateFormat(format, locale)
        return sdf.format(this)
    }

    fun ViewGroup.inflateView(layoutId: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
    }
}