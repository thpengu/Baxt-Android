package uz.karsoft.baxt.ui.main.home.category

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.karsoft.baxt.R
import uz.karsoft.baxt.data.models.main.search.Data
import uz.karsoft.baxt.databinding.ItemCategory2Binding
import uz.karsoft.baxt.extensions.base.BaseAdapter
import uz.karsoft.baxt.extensions.inflate
import uz.karsoft.baxt.extensions.onClick

class CategoryItemsAdapter: BaseAdapter<Data, CategoryItemsAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemCategory2Binding) :
        RecyclerView.ViewHolder(binding.root) {

        fun populateModel(model: Data, position: Int) = binding.apply {
            tvName.text = model.name.ru

            itemView.onClick {
                onItemClicked.invoke(model)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemCategory2Binding.bind(parent.inflate(R.layout.item_category2))
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.populateModel(models[position], position)
    }

    private var onItemClicked: (model: Data) -> Unit = {}
    fun setOnItemClickedListener(onItemClicked: (model: Data) -> Unit) {
        this.onItemClicked = onItemClicked
    }
}