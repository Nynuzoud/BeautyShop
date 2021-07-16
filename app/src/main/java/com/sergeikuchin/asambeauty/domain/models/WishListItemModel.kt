package com.sergeikuchin.asambeauty.domain.models

data class WishListItemModel(
    val id: Long,
    val title: String,
    val count: Int,
    val image: ImageMedia,
    val price: Float,
    val oldPrice: Float? = null
) : AdapterModel {

    override fun areItemsTheSame(other: AdapterModel): Boolean = other is WishListItemModel
            && other.id == id

    override fun areContentsTheSame(other: AdapterModel): Boolean = other is WishListItemModel
            && other == this
}