package uz.karsoft.baxt.ui.main.search

import android.graphics.Color
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import uz.karsoft.baxt.R
import uz.karsoft.baxt.data.models.main.search.Data
import uz.karsoft.baxt.databinding.ItemSearchBinding
import uz.karsoft.baxt.extensions.base.BaseAdapter
import uz.karsoft.baxt.extensions.inflate
import uz.karsoft.baxt.extensions.onClick
import kotlin.random.Random

class SearchAdapter: BaseAdapter<Data, SearchAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var adapter = SearchChildrenAdapter()
        private var visibility = false

        fun populateModel(model: Data, position: Int) = binding.apply {
            tvName.text = model.name.ru
            visibility = false
            rvItems.isVisible = visibility

            ivEnd.setImageResource(R.drawable.ic_bottom)

            val color = Color.argb(255, Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
            ivStart.setColorFilter(color)

            val resourceName = "ic_category_${model.id}"
            val imageResId = try {
                R.drawable::class.java.getField(resourceName).getInt(null)
            } catch (e: NoSuchFieldException) {
                R.drawable.ic_search
            }
            if (imageResId != 0) {
                ivStart.setImageResource(imageResId)
            } else {
                ivStart.setImageResource(R.drawable.ic_search)
            }

//            val resourceName = "ic_category_${model.id}"
//            val imageResId = itemView.context.resources.getIdentifier(resourceName, "drawable", itemView.context.packageName)
//
//            if (imageResId != 0) { // Make sure the resource exists
//                ivStart.setImageResource(imageResId)
//            } else {
//                ivStart.setImageResource(R.drawable.ic_search) // Fallback drawable
//            }

            ivEnd.onClick {
                if(model.children.isNotEmpty()){
                    visibility = !visibility
                    rvItems.isVisible = visibility

                    if(visibility){
                        ivEnd.setImageResource(R.drawable.ic_bottom)
                    } else {
                        ivEnd.setImageResource(R.drawable.ic_right)
                    }
                } else{

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