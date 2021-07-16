package com.sergeikuchin.asambeauty.domain.models

data class WishListModel(
    val totalPrice: Float,
    val wishItems: List<WishListItemModel>
) : AdapterModel {

    override fun areItemsTheSame(other: AdapterModel): Boolean = other is WishListModel

    override fun areContentsTheSame(other: AdapterModel): Boolean = other is WishListModel
            && other.totalPrice == totalPrice
            && other.wishItems.toTypedArray() contentEquals wishItems.toTypedArray()
}