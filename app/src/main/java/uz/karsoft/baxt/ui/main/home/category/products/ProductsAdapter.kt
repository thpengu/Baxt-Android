package uz.karsoft.baxt.ui.main.home.category.products

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.karsoft.baxt.R
import uz.karsoft.baxt.data.models.main.home.detail.product.ProductData
import uz.karsoft.baxt.databinding.ItemProductBinding
import uz.karsoft.baxt.extensions.base.BaseAdapter
import uz.karsoft.baxt.extensions.inflate
import uz.karsoft.baxt.extensions.onClick

class ProductsAdapter: BaseAdapter<ProductData, ProductsAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun populateModel(model: ProductData, position: Int) = binding.apply {
            tvName.text = model.name

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

    private var onItemClicked: (model: ProductData) -> Unit = {}
    fun setOnItemClickedListener(onItemClicked: (model: ProductData) -> Unit) {
        this.onItemClicked = onItemClicked
    }
}