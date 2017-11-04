package gasca.com.bardcodescanner.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.MenuItem
import gasca.com.bardcodescanner.Adapters.ProductAdapter
import gasca.com.bardcodescanner.Data.DataManager
import gasca.com.bardcodescanner.Extra.Interfaces
import gasca.com.bardcodescanner.Extra.Extensions.showActionDialog
import gasca.com.bardcodescanner.Extra.Extensions.disappear
import gasca.com.bardcodescanner.Extra.Extensions.appear
import gasca.com.bardcodescanner.Extra.Extensions.showAlertDialog
import gasca.com.bardcodescanner.Models.Product

import gasca.com.bardcodescanner.R
import kotlinx.android.synthetic.main.activity_products_list.*
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException

class ProductsListActivity : AppCompatActivity(), Interfaces.ProductAdapterCallbacks {

    override fun onProductClicked(product: Product) {
        val intent = Intent(this, ProductActivity::class.java)
        intent.putExtra(ProductActivity.PhaseObject, 2)
        intent.putExtra(ProductActivity.BarcodeObject, product.barcode)
        startActivity(intent)
    }

    companion object {
        val TAG = "ProductsList"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products_list)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            android.R.id.home -> {
                this.finish()
            }
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        init()
    }


    private fun init(){
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setupRecyclerView()
        events()
    }

    private fun setupRecyclerView(){

        val products = DataManager.getProducts()
        recyclerProducts.layoutManager = LinearLayoutManager(this)
        recyclerProducts.adapter = ProductAdapter(products, this)

        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, viewHolder1: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val position = viewHolder.adapterPosition
                val adapter = recyclerProducts.adapter as ProductAdapter
                adapter.removeAt(position)

            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerProducts)
    }

    private fun showLoader(){
        progressBar.appear()
        recyclerProducts.disappear()
        btnSend.disappear()
    }

    private fun showMain(){
        progressBar.disappear()
        recyclerProducts.appear()
        btnSend.appear()
    }

    private fun sendProducts(){
        val products = DataManager.getProducts()
        val p = products
                .map{
                    val newP = it.deepCopy()
                    newP.price = newP.price!!.replace("$","")

                    newP
                }
        showLoader()
        DataManager.sendProducts(p)
                .subscribe({
                    showMain()
                    showDialog()
                },{err ->
                    showMain()
                    handleError(err)
                })
    }

    private fun handleError(err: Throwable){
        err.printStackTrace()
        if (err is ConnectException){
            showAlertDialog(title = getString(R.string.error),
                            message = getString(R.string.no_internet))
        } else if (err is SocketTimeoutException){
            showAlertDialog(title = getString(R.string.error),
                    message = getString(R.string.timeout))
        }else if(err is HttpException){
            if (err.code() == 404){
                showAlertDialog(title = getString(R.string.error),
                        message = getString(R.string.not_found))
            }
        }else{
            showAlertDialog(title = getString(R.string.error),
                    message = getString(R.string.unknown_error))
        }

    }

    private fun events(){
        btnSend.setOnClickListener{ sendProducts() }
    }

    private fun showDialog(){
        this.showActionDialog(title=getString(R.string.success),
                              message = getString(R.string.priceholders_sent),
                              acceptText = getString(R.string.accept),
                              callback = { dialog,_ -> returnToMain() })
    }

    private fun returnToMain(){
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        startActivity(intent)
    }
}
