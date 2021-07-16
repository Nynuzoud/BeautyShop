package com.sergeikuchin.asambeauty.domain.models

data class CartListItemModel(
    val id: Long,
    val title: String,
    val count: Int,
    val price: Float,
    val image: ImageMedia,
    val oldPrice: Float? = null
) : AdapterModel {
    override fun areItemsTheSame(other: AdapterModel): Boolean = other is CartListItemModel
            && other.id == id

    override fun areContentsTheSame(other: AdapterModel): Boolean = other is CartListItemModel
            && other == this
}
