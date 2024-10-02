package uz.karsoft.baxt.ui.lang

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.karsoft.baxt.R
import uz.karsoft.baxt.data.models.lang.LanguageData
import uz.karsoft.baxt.databinding.ItemLanguageBinding
import uz.karsoft.baxt.extensions.base.BaseAdapter
import uz.karsoft.baxt.extensions.inflate
import uz.karsoft.baxt.extensions.onClick

class LanguageAdapter: BaseAdapter<LanguageData, LanguageAdapter.ViewHolder>() {
    private var selectedPosition: Int = RecyclerView.NO_POSITION
    inner class ViewHolder(private val binding: ItemLanguageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun populateModel(model: LanguageData, position: Int) = binding.apply {
           tvLang.text = model.name

            itemView.isSelected = selectedPosition == position

            itemView.onClick {
                if (selectedPosition != position) {
                    val previousPosition = selectedPosition
                    selectedPosition = position
                    notifyItemChanged(previousPosition)
                    notifyItemChanged(position)

                    onItemClicked.invoke(model)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemLanguageBinding.bind(parent.inflate(R.layout.item_language))
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.populateModel(models[position], position)
    }

    private var onItemClicked: (model: LanguageData) -> Unit = {}
    fun setOnItemClickedListener(onItemClicked: (model: LanguageData) -> Unit) {
        this.onItemClicked = onItemClicked
    }
}