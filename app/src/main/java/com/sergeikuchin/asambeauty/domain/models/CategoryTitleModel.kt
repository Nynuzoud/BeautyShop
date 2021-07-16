package com.sergeikuchin.asambeauty.domain.models

data class CategoryTitleModel(
    val title: String
) : AdapterModel {

    override fun areItemsTheSame(other: AdapterModel): Boolean = other is CategoryTitleModel

    override fun areContentsTheSame(other: AdapterModel): Boolean = other is CategoryTitleModel
            && other == this
}