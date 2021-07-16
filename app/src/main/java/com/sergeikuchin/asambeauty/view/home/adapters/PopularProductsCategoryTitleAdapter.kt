package com.sergeikuchin.asambeauty.view.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.sergeikuchin.asambeauty.databinding.ItemCategoryTitleBinding
import com.sergeikuchin.asambeauty.domain.models.CategoryTitleModel
import com.sergeikuchin.asambeauty.view.utils.recycler_view.GeneralRecyclerViewAdapter

class PopularProductsCategoryTitleAdapter :
    GeneralRecyclerViewAdapter<CategoryTitleViewHolder, CategoryTitleModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryTitleViewHolder =
        ItemCategoryTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            .run(::CategoryTitleViewHolder)

    override fun onBindViewHolder(holder: CategoryTitleViewHolder, position: Int) {
        holder.bind(items[position])
    }
}