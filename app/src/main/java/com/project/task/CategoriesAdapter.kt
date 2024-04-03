package com.project.task

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.task.databinding.ItemBannerBinding
import com.project.task.databinding.ItemCategoryBinding
import com.project.task.requests.Category
import com.project.task.requests.ItemDish

class CategoriesDiffCallback(
    private val oldList: List<Category>,
    private val newList: List<Category>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItemPosition == newItemPosition
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}

interface CategoryListener {

    fun changeCategory(category: Category)

}

class CategoriesAdapter(private val categoryListener: CategoryListener) :
    RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    var selectedPosition = 0
    var lastPosition = 0

    var items: List<Category> = emptyList()
        set(newValue) {
            val diffCallback = CategoriesDiffCallback(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryBinding.inflate(inflater, parent, false)

        binding.nameCategory.setOnClickListener {
            changeCategory(binding.nameCategory.tag as List<Any>)
        }

        return CategoriesAdapter.CategoriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val item = items[position]
        with(holder.binding) {
            nameCategory.tag = listOf(item, position)
            nameCategory.text = item.strCategory
            nameCategory.setTextColor(R.color.back_color_text.toInt())
            nameCategory.setBackgroundColor(R.color.white.toInt())
            if (position == selectedPosition){
                nameCategory.setTextColor(R.color.main_color.toInt())
                nameCategory.setBackgroundColor(R.color.main_color_transparent20.toInt())
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun changeCategory(selectedItem: List<Any>){
        val item = selectedItem[0] as Category
        selectedPosition = selectedItem[1] as Int
        if (selectedPosition != lastPosition){
            lastPosition = selectedPosition
            categoryListener.changeCategory(item)
            notifyDataSetChanged()
        }
    }

    class CategoriesViewHolder(
        val binding: ItemCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root)

}
