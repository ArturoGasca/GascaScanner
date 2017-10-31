package gasca.com.bardcodescanner.Adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import gasca.com.bardcodescanner.Data.DataManager
import gasca.com.bardcodescanner.Models.Product
import kotlinx.android.synthetic.main.product_item.view.*
import gasca.com.bardcodescanner.Extra.Extensions.inflateView
import gasca.com.bardcodescanner.R
import gasca.com.bardcodescanner.R.string.product

/**
 * Created by in-qu on 28/10/2017.
 */
class ProductAdapter(val products: MutableList<Product>):
        RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    fun removeAt(position: Int){
        val product = products[position]
        product.requiresPriceHolder = false
        DataManager.updateProductLocally(product)

        products.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount() = products.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = parent!!.inflateView(R.layout.product_item)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(vh: ViewHolder?, position: Int) {
        val product = products[position]

        vh!!.lblName.text = product.description
        vh.lblPrice.text = "${product.price}"
        vh.txtQuantity.setText("${product.quantity}")
        vh.btnAdd.setOnClickListener {
            product.quantity++
            DataManager.updateProductLocally(product)
            vh.txtQuantity.setText("${product.quantity}")
        }
        vh.btnRemove.setOnClickListener {
            if(product.quantity > 1){
                product.quantity--
                DataManager.updateProductLocally(product)
                vh.txtQuantity.setText("${product.quantity}")
            }
        }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val lblName = view.lblName
        val lblPrice = view.lblPrice
        val txtQuantity = view.txtQuantity
        val btnAdd = view.btnAdd
        val btnRemove = view.btnRemove
    }
}

