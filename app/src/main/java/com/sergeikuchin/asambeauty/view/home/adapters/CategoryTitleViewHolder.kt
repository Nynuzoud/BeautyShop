package com.sergeikuchin.asambeauty.view.home.adapters

import androidx.recyclerview.widget.RecyclerView
import com.sergeikuchin.asambeauty.databinding.ItemCategoryTitleBinding
import com.sergeikuchin.asambeauty.domain.models.CategoryTitleModel

class CategoryTitleViewHolder(
    private val binding: ItemCategoryTitleBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CategoryTitleModel) {
        binding.dividerTextView.text = item.title
    }
}