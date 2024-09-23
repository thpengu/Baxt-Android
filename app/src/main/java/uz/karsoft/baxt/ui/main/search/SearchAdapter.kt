package uz.karsoft.baxt.ui.main.search

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import uz.karsoft.baxt.R
import uz.karsoft.baxt.data.models.main.search.Data
import uz.karsoft.baxt.databinding.ItemSearchBinding
import uz.karsoft.baxt.extensions.base.BaseAdapter
import uz.karsoft.baxt.extensions.inflate
import uz.karsoft.baxt.extensions.onClick

class SearchAdapter: BaseAdapter<Data, SearchAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val adapter = SearchChildrenAdapter()
        private var visibility = false

        fun populateModel(model: Data, position: Int) = binding.apply {
            tvName.text = model.name.ru
            visibility = false

            ivEnd.setImageResource(R.drawable.ic_right)
            rvItems.isVisible = visibility

            ivEnd.onClick {
                visibility = !visibility

                if(visibility){
                    ivEnd.setImageResource(R.drawable.ic_right)
                } else {
                    ivEnd.setImageResource(R.drawable.ic_bottom)
                }
            }

            rvItems.adapter = adapter
            adapter.models = model.children

            itemView.onClick {
                onItemClicked.invoke(model)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemSearchBinding.bind(parent.inflate(R.layout.item_search))
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.populateModel(models[position], position)
    }

    private var onItemClicked: (model: Data) -> Unit = {}
    fun setOnItemClickedListener(onItemClicked: (model: Data) -> Unit) {
        this.onItemClicked = onItemClicked
    }
}