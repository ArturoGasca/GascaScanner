package gasca.com.bardcodescanner.Adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import gasca.com.bardcodescanner.Data.DataManager
import gasca.com.bardcodescanner.Models.Product
import kotlinx.android.synthetic.main.product_item.view.*
import gasca.com.bardcodescanner.Extra.Extensions.inflateView
import gasca.com.bardcodescanner.Extra.Interfaces
import gasca.com.bardcodescanner.R
import gasca.com.bardcodescanner.R.string.product

/**
 * Created by in-qu on 28/10/2017.
 */
class ProductAdapter(val products: MutableList<Product>,
                     val listener: Interfaces.ProductAdapterCallbacks):
        RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    fun setProducts(products: List<Product>){
        this.products.clear()
        this.products.addAll(products)
        notifyDataSetChanged()
    }

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
        vh.layoutRoot.setOnClickListener{
            listener.onProductClicked(product)
        }
        vh.lblPriceHolders.text = "Portaprecios: ${product.quantity}"
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val lblName = view.lblName
        val lblPrice = view.lblPrice
        val layoutRoot = view.layoutRoot
        val lblPriceHolders = view.lblPriceHolders
    }
}

