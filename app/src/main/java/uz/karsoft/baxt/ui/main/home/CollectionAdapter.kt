package uz.karsoft.baxt.ui.main.home

import android.graphics.Color
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.karsoft.baxt.R
import uz.karsoft.baxt.data.models.main.home.Data
import uz.karsoft.baxt.databinding.ItemCategoryBinding
import uz.karsoft.baxt.extensions.base.BaseAdapter
import uz.karsoft.baxt.extensions.inflate
import uz.karsoft.baxt.extensions.onClick
import kotlin.random.Random

class CollectionAdapter : BaseAdapter<Data, CollectionAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun populateModel(model: Data, position: Int) = binding.apply {
//            Glide.with(itemView.context)
//                .load(model.iconUrl) // Load the URL
//                .into(ivCategory)
            tvCategory.text = model.name.ru

            val color =
                Color.argb(255, Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
            ivCategory.setColorFilter(color)

            val resourceName = "ic_collection_${model.id}"
            val imageResId = try {
                R.drawable::class.java.getField(resourceName).getInt(null)
            } catch (e: NoSuchFieldException) {
                R.drawable.ic_setting
            }
            if (imageResId != 0) {
                ivCategory.setImageResource(imageResId)
            }
            itemView.setOnClickListener {
                onItemClicked.invoke(model)
            }


//            val resourceName = "ic_collection_${model.id}"
//            val imageResId = itemView.context.resources.getIdentifier(resourceName, "drawable", itemView.context.packageName)
//            if (imageResId != 0) { // Make sure the resource exists
//                ivCategory.setImageResource(imageResId)
//            }
//            itemView.onClick {
//                onItemClicked.invoke(model)
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemCategoryBinding.bind(parent.inflate(R.layout.item_category))
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.populateModel(models[position], position)
    }

    private var onItemClicked: (model: Data) -> Unit = {}
    fun setOnItemClickedListener(onItemClicked: (model: Data) -> Unit) {
        this.onItemClicked = onItemClicked
    }
}