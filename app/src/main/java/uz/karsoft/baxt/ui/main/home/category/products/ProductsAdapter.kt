package uz.karsoft.baxt.ui.main.home.category.products

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.karsoft.baxt.R
import uz.karsoft.baxt.data.local.DatabaseHelper
import uz.karsoft.baxt.data.models.main.home.detail.all_products.AllData
import uz.karsoft.baxt.data.models.main.saved_data.SavedProductsData // Yangi model qo'shildi
import uz.karsoft.baxt.databinding.ItemProductBinding
import uz.karsoft.baxt.extensions.base.BaseAdapter
import uz.karsoft.baxt.extensions.inflate
import uz.karsoft.baxt.extensions.onClick

class ProductsAdapter(val context: Context) : BaseAdapter<AllData, ProductsAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun populateModel(model: AllData, position: Int) = binding.apply {
            tvName.text = model.name

            Glide.with(itemView.context)
                .load(model.image_url)
                .into(ivProduct)

            // DatabaseHelper yordamida mahsulotning holatini tekshiramiz
            val dbHelper = DatabaseHelper(context)

            val isLiked = dbHelper.isProductLiked(model.id)
            ivLiked.setImageResource(if (isLiked) R.drawable.ic_heart else R.drawable.ic_heart_outline)

            ivLiked.onClick {
                // Agar mahsulot sevimli bo'lmasa, uni qo'shamiz
                if (!isLiked) {
                    val savedProduct = SavedProductsData(
                        category_id = model.category_id, // Ushbu maydonni to'ldiring
                        created_at = "now", // Yaratish sanasi
                        discount_price = 0, // Agar yo'q bo'lsa, 0 qoldiring
                        id = model.id,
                        image_url = model.image_url,
                        name = model.name,
                        price = model.price,
                        rating = model.rating, // Agar rating bo'lsa
                        review_count = model.review_count, // Agar review_count bo'lsa
                        updated_at = "now" // Yangilanish sanasi
                    )
                    dbHelper.addProduct(savedProduct) // Qo'shish
                    ivLiked.setImageResource(R.drawable.ic_heart)
                } else {
                    // Aks holda, mahsulotni o'chiramiz
                    dbHelper.deleteProduct(model.id) // O'chirish
                    ivLiked.setImageResource(R.drawable.ic_heart_outline)
                }
            }

            itemView.onClick {
                onItemClicked.invoke(model)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemProductBinding.bind(parent.inflate(R.layout.item_product))
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.populateModel(models[position], position)
    }

    private var onItemClicked: (model: AllData) -> Unit = {}
    fun setOnItemClickedListener(onItemClicked: (model: AllData) -> Unit) {
        this.onItemClicked = onItemClicked
    }
}
