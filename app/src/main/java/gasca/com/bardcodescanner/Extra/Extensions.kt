package gasca.com.bardcodescanner.Extra

import android.app.Activity
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import gasca.com.bardcodescanner.R
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

    fun Activity.showAlertDialog(title: String, message: String){
        val builder = AlertDialog.Builder(this)

        builder.setMessage(message)
                .setTitle(title)
        builder.setPositiveButton(R.string.cancel, { dialog, _ -> dialog.cancel() })

        val dialog = builder.create()
        dialog.show()
    }

    fun Activity.showActionDialog(title: String,
                                  message: String,
                                  acceptText: String,
                                  callback: (DialogInterface, Int) -> Unit){
        val builder = AlertDialog.Builder(this)

        if(!title.isBlank())
            builder.setTitle(title)
        if (!message.isBlank())
            builder.setMessage(message)

        builder.setPositiveButton(acceptText, callback)

        val dialog = builder.create()
        dialog.setCancelable(false)
        dialog.show()
    }
}