package uz.karsoft.baxt.ui.main.saved

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.karsoft.baxt.R
import uz.karsoft.baxt.data.models.main.all_markets_data.MarketsData
import uz.karsoft.baxt.data.models.main.saved_data.SavedProductsData
import uz.karsoft.baxt.databinding.ItemSavedBinding
import uz.karsoft.baxt.extensions.base.BaseAdapter
import uz.karsoft.baxt.extensions.inflate
import uz.karsoft.baxt.extensions.onClick

class SavedProductsAdapter(
    private var onProductRemoved: (SavedProductsData) -> Unit
) : BaseAdapter<SavedProductsData, SavedProductsAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemSavedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun populateModel(model: SavedProductsData) = binding.apply {
            savedProductName.text = model.name
            priceSavedProduct.text = model.price.toString()  // Narxni aniq yangilang


            Glide.with(itemView.context)
                .load(model.image_url)
                .into(imgProductSaved)

            ivLiked.setImageResource(R.drawable.ic_heart)

            ivLiked.onClick {
                onProductRemoved(model)
                ivLiked.setImageResource(R.drawable.ic_heart_outline)
            }

            itemView.setOnClickListener {
                onItemClicked(model)  // Call the onItemClicked listener
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemSavedBinding.bind(parent.inflate(R.layout.item_saved))
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.populateModel(models[position]) // Har bir modelni yangilang
    }

    fun setProducts(newProducts: List<SavedProductsData>) {
        models = newProducts
        notifyItemRangeChanged(0, newProducts.size)  // Barcha itemlarni yangilang
    }

    private var onItemClicked: (model: SavedProductsData) -> Unit = {}
    fun setOnItemClickedListener(onItemClicked: (model: SavedProductsData) -> Unit) {
        this.onItemClicked = onItemClicked


    }
}

