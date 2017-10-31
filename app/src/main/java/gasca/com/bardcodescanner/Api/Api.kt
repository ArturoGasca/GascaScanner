package gasca.com.bardcodescanner.Api

import gasca.com.bardcodescanner.Models.Product
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Created by in-qu on 26/10/2017.
 */
interface Api{


    @GET("products/{barcode}")
    fun getProduct(@Path("barcode") barcode: String): Observable<Product>

    @POST("products/")
    fun sendProducts(@Body products: List<Product>): Completable
}