package uz.karsoft.baxt.ui.main.home.category.markets

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.karsoft.baxt.R
import uz.karsoft.baxt.data.models.main.all_markets_data.MarketsData
import uz.karsoft.baxt.data.models.main.home.detail.all_products.AllData
import uz.karsoft.baxt.databinding.ItemProductBinding
import uz.karsoft.baxt.extensions.base.BaseAdapter
import uz.karsoft.baxt.extensions.inflate
import uz.karsoft.baxt.extensions.onClick
import uz.karsoft.baxt.ui.main.home.category.products.ProductsAdapter

class MarketsAdapter(): BaseAdapter<MarketsData, MarketsAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun populateModel(model: MarketsData, position: Int) = binding.apply {
            tvName.text = model.name

            Glide.with(itemView.context)
                .load(model.image.url)
                .into(ivProduct)

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

    private var onItemClicked: (model: MarketsData) -> Unit = {}
    fun setOnItemClickedListener(onItemClicked: (model: MarketsData) -> Unit) {
        this.onItemClicked = onItemClicked


    }
}