package uz.karsoft.baxt.ui.main.home.foods

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.karsoft.baxt.R
import uz.karsoft.baxt.data.models.main.foods.FoodData
import uz.karsoft.baxt.data.models.main.home.detail.all_products.AllData
import uz.karsoft.baxt.databinding.ItemProductBinding
import uz.karsoft.baxt.extensions.base.BaseAdapter
import uz.karsoft.baxt.extensions.inflate
import uz.karsoft.baxt.extensions.onClick

class FoodsAdapter : BaseAdapter<FoodData, FoodsAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun populateModel(model: FoodData, position: Int) = binding.apply {
            tvName.text = model.name

            Glide.with(itemView.context)
                .load(model.image_url)
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

        Log.d("FoodsAdapter", "onBindViewHolder: Position $position")
        holder.populateModel(models[position], position)
    }

    private var onItemClicked: (model: FoodData) -> Unit = {}
    fun setOnItemClickedListener(onItemClicked: (model: FoodData) -> Unit) {
        this.onItemClicked = onItemClicked


    }
}