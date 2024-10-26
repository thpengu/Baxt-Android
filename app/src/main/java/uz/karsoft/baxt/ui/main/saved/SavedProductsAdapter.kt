package uz.karsoft.baxt.ui.main.saved

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.karsoft.baxt.R
import uz.karsoft.baxt.data.models.main.saved_data.SavedProductsData
import uz.karsoft.baxt.databinding.ItemProductBinding
import uz.karsoft.baxt.extensions.base.BaseAdapter
import uz.karsoft.baxt.extensions.inflate
import uz.karsoft.baxt.extensions.onClick

class SavedProductsAdapter(
    private var onProductRemoved: (SavedProductsData) -> Unit // O'chirish uchun callback
) : BaseAdapter<SavedProductsData, SavedProductsAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun populateModel(model: SavedProductsData) = binding.apply {
            tvName.text = model.name

            Glide.with(itemView.context)
                .load(model.image_url)
                .into(ivProduct)

            // Doimo sevimli bo'lganini ko'rsatish
            ivLiked.setImageResource(R.drawable.ic_heart)

            // O'chirish funksiyasi
            ivLiked.onClick {
                onProductRemoved(model) // Callbackni chaqirish
                ivLiked.setImageResource(R.drawable.ic_heart_outline)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemProductBinding.bind(parent.inflate(R.layout.item_product))
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.populateModel(models[position]) // models dan foydalanish
    }

    // Faqat bitta setModels metodini aniqlang
    fun setProducts(newProducts: List<SavedProductsData>) {
        models = newProducts // models ni yangilang
        notifyDataSetChanged() // RecyclerView ni yangilash
    }
}
