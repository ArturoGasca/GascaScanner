package gasca.com.bardcodescanner.Data

import gasca.com.bardcodescanner.Models.Product
import gasca.com.bardcodescanner.Api.RetrofitService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Created by in-qu on 26/10/2017.
 */
object DataManager {

    fun getProduct(barcode: String): Observable<Product> {
        val cachedProduct = CacheManager.get(barcode)

        return if (cachedProduct !== null)
            Observable.just(cachedProduct)
        else
            RetrofitService.instance.getProduct(barcode)
                    .doOnNext { if (it != null) cacheProduct(it) }
                    .observeOn(AndroidSchedulers.mainThread())
    }

    fun sendProducts(products: List<Product>) =
        RetrofitService.instance.sendProducts(products)
        .doOnComplete { CacheManager.clearAll() }

    fun updateProductLocally(product: Product) = CacheManager.put(product.barcode!!, product)
    fun getProducts() = CacheManager.getAll().filter { it.requiresPriceHolder }.toMutableList()
    private fun cacheProduct(product: Product) = CacheManager.put(product.barcode!!, product)
}