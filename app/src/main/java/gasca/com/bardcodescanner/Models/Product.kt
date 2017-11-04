package gasca.com.bardcodescanner.Models

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*
import gasca.com.bardcodescanner.Extra.Extensions.format


/**
 * Created by in-qu on 25/10/2017.
 */
class Product(@SerializedName("barcode") val barcode: String?,
              @SerializedName("description") val description: String?,
              @SerializedName("price") var price: String?,
              @SerializedName("offerPrice") val offerPrice: String?,
              @SerializedName("startDate") val startDate: Date?,
              @SerializedName("endDate") val endDate: Date?,
              @SerializedName("quantity") var quantity: Int = 1,
              var requiresPriceHolder: Boolean = false){

    fun hasOffer(): Boolean{
        val now = Calendar.getInstance()

        val startDate = Calendar.getInstance()
        val endDate = Calendar.getInstance()

        startDate.time = this.startDate
        endDate.time = this.endDate

        startDate.set(Calendar.HOUR, 0)
        startDate.set(Calendar.MINUTE, 0)
        startDate.set(Calendar.SECOND, 0)

        endDate.set(Calendar.HOUR, 23)
        endDate.set(Calendar.MINUTE, 59)
        endDate.set(Calendar.SECOND, 59)

        return if(this.offerPrice == "$0.00") false
        else now in startDate..endDate
    }

    fun deepCopy() =
        Product(
            this.barcode,
            this.description,
            this.price,
            this.offerPrice,
            this.startDate,
            this.endDate,
            this.quantity,
            this.requiresPriceHolder
        )
}