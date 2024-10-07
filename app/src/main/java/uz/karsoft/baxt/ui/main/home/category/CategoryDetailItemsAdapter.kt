package uz.karsoft.baxt.ui.main.home.category

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.karsoft.baxt.R
import uz.karsoft.baxt.data.models.main.search.Children
import uz.karsoft.baxt.databinding.ItemCategory2Binding
import uz.karsoft.baxt.extensions.base.BaseAdapter
import uz.karsoft.baxt.extensions.inflate
import uz.karsoft.baxt.extensions.onClick

class CategoryDetailItemsAdapter: BaseAdapter<Children, CategoryDetailItemsAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemCategory2Binding) :
        RecyclerView.ViewHolder(binding.root) {

        fun populateModel(model: Children, position: Int) = binding.apply {
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

    private var onItemClicked: (model: Children) -> Unit = {}
    fun setOnItemClickedListener(onItemClicked: (model: Children) -> Unit) {
        this.onItemClicked = onItemClicked
    }
}