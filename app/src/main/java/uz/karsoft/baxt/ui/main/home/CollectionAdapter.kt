package uz.karsoft.baxt.ui.main.home

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.karsoft.baxt.R
import uz.karsoft.baxt.data.models.main.home.Data
import uz.karsoft.baxt.databinding.ItemCategoryBinding
import uz.karsoft.baxt.extensions.base.BaseAdapter
import uz.karsoft.baxt.extensions.inflate
import uz.karsoft.baxt.extensions.onClick

class CollectionAdapter: BaseAdapter<Data, CollectionAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun populateModel(model: Data, position: Int) = binding.apply {
            Glide.with(itemView.context)
                .load(model.iconUrl) // Load the URL
                .into(ivCategory)
            tvCategory.text = model.name.ru

            itemView.onClick {
                onItemClicked.invoke(model)
            }
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