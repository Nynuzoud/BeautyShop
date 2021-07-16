package com.sergeikuchin.asambeauty.domain.models

data class ProductModel(
    val id: Long,
    val title: String,
    val image: ImageMedia,
    val price: Float,
    val oldPrice: Float? = null,
    val addedToWishList: Boolean = false,
    val addedToCartList: Boolean = false
) : AdapterModel {

    override fun areItemsTheSame(other: AdapterModel): Boolean = other is ProductModel
            && other.id == id

    override fun areContentsTheSame(other: AdapterModel): Boolean = other is ProductModel
            && other == this
}