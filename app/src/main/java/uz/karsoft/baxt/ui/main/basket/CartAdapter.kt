import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.karsoft.baxt.R
import uz.karsoft.baxt.data.models.main.cart.CartData
import uz.karsoft.baxt.data.models.main.home.detail.product_by_id_data.ProductByIdData
import uz.karsoft.baxt.databinding.ItemKorzinkaBinding
import uz.karsoft.baxt.databinding.ItemSavedBinding
import uz.karsoft.baxt.extensions.base.BaseAdapter
import uz.karsoft.baxt.extensions.inflate
import uz.karsoft.baxt.ui.main.saved.SavedProductsAdapter.ViewHolder

class CartAdapter(
    private val context: Context,
    private var items: List<CartData>,
    private val onDeleteClickListener: OnDeleteClickListener // Add this parameter
) : BaseAdapter<CartData, CartAdapter.CartViewHolder>() {

    // Define the interface
    interface OnDeleteClickListener {
        fun onDeleteClicked(cartData: CartData)
    }

    inner class CartViewHolder(private val binding: ItemKorzinkaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cartData: CartData) {
            binding.apply {
                productNameKorzinka.text = cartData.name
                productPriceKorzinka.text = cartData.price.toString()
                productCountKorzinka.text = cartData.quantity.toString()

                Glide.with(context)
                    .load(cartData.image)
                    .into(binding.imgProductKorzinka)

                // Set up the delete button click listener
                btnDelete.setOnClickListener {
                    onDeleteClickListener.onDeleteClicked(cartData) // Trigger the callback
                }
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(ItemKorzinkaBinding.bind(parent.inflate(R.layout.item_korzinka)))
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = models[position]
        holder.bind(cartItem)


    }


    fun updateCart(newCartItems: List<CartData>) {
        models = newCartItems
        notifyItemRangeChanged(0, newCartItems.size)
    }


}
