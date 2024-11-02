package uz.karsoft.baxt.ui.main.basket

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uz.karsoft.baxt.R
import uz.karsoft.baxt.data.models.main.cart.CartData

class BasketAdapter(
    private val context: Context,
    private var cartItems: List<CartData>,
    private val onRemoveClick: (CartData) -> Unit // Callback for removing item)
):RecyclerView.Adapter<BasketAdapter.BasketViewHolder>(){

    inner class BasketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        
}