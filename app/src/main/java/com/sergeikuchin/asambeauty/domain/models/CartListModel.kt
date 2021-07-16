package com.sergeikuchin.asambeauty.domain.models

data class CartListModel(
    val totalPrice: Float,
    val cartItems: List<CartListItemModel>
) : AdapterModel {

    override fun areItemsTheSame(other: AdapterModel): Boolean = other is CartListModel

    override fun areContentsTheSame(other: AdapterModel): Boolean = other is CartListModel
            && other.totalPrice == totalPrice
            && other.cartItems.toTypedArray() contentEquals cartItems.toTypedArray()
}
