package uz.karsoft.baxt.ui.main.home.detail

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.karsoft.baxt.R
import uz.karsoft.baxt.data.models.main.home.detail.NavigationData
import uz.karsoft.baxt.databinding.ItemNavigationBinding
import uz.karsoft.baxt.extensions.base.BaseAdapter
import uz.karsoft.baxt.extensions.inflate
import uz.karsoft.baxt.extensions.onClick

class NavigationAdapter: BaseAdapter<NavigationData, NavigationAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemNavigationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun populateModel(model: NavigationData, position: Int) = binding.apply {
            tvName.text = model.name
            var textColor = itemView.context.getColor(R.color.black)
            if(position == 0){
                textColor = itemView.context.getColor(R.color.pink)
            }
            tvName.setTextColor(textColor)

            itemView.onClick {
                onItemClicked.invoke(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemNavigationBinding.bind(parent.inflate(R.layout.item_navigation))
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.populateModel(models[position], position)
    }

    private var onItemClicked: (pos: Int) -> Unit = {}
    fun setOnItemClickedListener(onItemClicked: (pos: Int) -> Unit) {
        this.onItemClicked = onItemClicked
    }
}