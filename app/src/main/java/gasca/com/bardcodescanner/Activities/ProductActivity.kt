package gasca.com.bardcodescanner.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.google.android.gms.vision.barcode.Barcode
import gasca.com.bardcodescanner.Models.Product
import gasca.com.bardcodescanner.R
import gasca.com.bardcodescanner.Data.DataManager
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_product.*
import kotlinx.android.synthetic.main.card_view.*
import kotlinx.android.synthetic.main.layout_product_sale.*
import java.util.*
import gasca.com.bardcodescanner.Extra.Extensions.disappear
import gasca.com.bardcodescanner.Extra.Extensions.appear
import gasca.com.bardcodescanner.Extra.Extensions.format
import kotlinx.android.synthetic.main.layout_error.*
import kotlinx.android.synthetic.main.layout_quantity.*
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.text.SimpleDateFormat

class ProductActivity : AppCompatActivity() {

    companion object{
        val BarcodeObject = "Barcode"
        val TAG = "ProductActivity"
    }

    val disposables = CompositeDisposable()

    val barcode by lazy{
        intent.getStringExtra(BarcodeObject)
    }

    lateinit var product: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        init()
    }

    private fun init(){
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getProductInfo()
    }

    private fun showLoader(){
        progressBar.appear()
        layoutMain.disappear()
        layoutError.disappear()
    }

    private fun showMain(){
        progressBar.disappear()
        layoutMain.appear()
    }

    private fun showError(){
        progressBar.disappear()
        layoutError.appear()
    }

    private fun getProductInfo(){
        showLoader()

        val request =
        DataManager.getProduct(barcode)
        .subscribe({ p ->
            showMain()
            product = p
            fillInformation(p)
            events()
        },{err ->
            showError()
            handleError(err)
        })

        disposables.add(request)
    }

    private fun handleError(err: Throwable){
        err.printStackTrace()
        if (err is ConnectException){
            lblCause.text =  getString(R.string.no_internet)
            btnSettings.disappear()
        } else if (err is SocketTimeoutException){
            lblCause.text =  getString(R.string.timeout)
            btnSettings.appear()
            btnSettings.setOnClickListener{ goToSettings() }
        }else if(err is HttpException){
            if (err.code() == 404){
                lblCause.text =  getString(R.string.not_found)
            }
            btnSettings.disappear()
        }else{
            lblCause.text =  getString(R.string.unknown_error)
        }

        btnRetry.setOnClickListener { getProductInfo() }
    }

    fun goToSettings(){
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    private fun events(){
        btnAddToList.setOnClickListener{
            product.requiresPriceHolder = true
            DataManager.updateProductLocally(product)
            Snackbar.make(layoutRoot, getString(R.string.item_added), Snackbar.LENGTH_LONG).show()
        }

        btnSeeList.setOnClickListener{
            goToList()
        }

        btnAdd.setOnClickListener{
            product.quantity++
            txtQuantity.setText("${product.quantity}", TextView.BufferType.EDITABLE)
        }

        btnRemove.setOnClickListener{
            if(product.quantity > 1){
                product.quantity--
                txtQuantity.setText("${product.quantity}", TextView.BufferType.EDITABLE)
            }
        }
    }

    fun goToList(){
        val intent = Intent(this, ProductsListActivity::class.java)
        startActivity(intent)
    }

    private fun fillInformation(product: Product){
        txtPrice.text = "${product.price}"
        txtDescription.text = "${product.description}"
        txtQuantity.setText("${product.quantity}", TextView.BufferType.EDITABLE)
        if (product.hasOffer()){
            txtSalePrice.text = "$${product.offerPrice}"
            txtStartDate.text = product.startDate?.format("dd/MM")
            txtEndDate.text = product.endDate?.format("dd/MM")

            layoutSale.appear()
        }else{
            layoutSale.disappear()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            android.R.id.home -> {
                this.finish()
            }
        }
        return true
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        disposables.clear()
        super.onDestroy()
    }
}