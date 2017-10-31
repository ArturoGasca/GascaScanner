package gasca.com.bardcodescanner.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import gasca.com.bardcodescanner.Adapters.ProductAdapter
import gasca.com.bardcodescanner.Data.DataManager

import gasca.com.bardcodescanner.R
import kotlinx.android.synthetic.main.activity_products_list.*

class ProductsListActivity : AppCompatActivity() {
    companion object {
        val TAG = "ProductsList"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products_list)

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
        recyclerProducts.adapter = ProductAdapter(products)

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

    private fun events(){
        btnSend.setOnClickListener{
            val products = DataManager.getProducts()
            for (p in products){
                println(TAG + p.description)
                println(TAG + p.quantity)
                println(TAG + "----")
            }

            //DataManager.sendProducts(products)
        }
    }
}
