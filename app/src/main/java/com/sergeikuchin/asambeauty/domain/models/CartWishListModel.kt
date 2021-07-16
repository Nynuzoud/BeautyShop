package com.sergeikuchin.asambeauty.domain.models

data class CartWishListModel(
    val cartListModel: CartListModel?,
    val wishListModel: WishListModel?
) : AdapterModel {

    override fun areItemsTheSame(other: AdapterModel): Boolean = other is CartWishListModel

    override fun areContentsTheSame(other: AdapterModel): Boolean = other is CartWishListModel
            && ((cartListModel == null && other.cartListModel == null) || cartListModel?.let { other.cartListModel?.areContentsTheSame(it) } ?: false)
            && ((wishListModel == null && other.wishListModel == null) || wishListModel?.let { other.wishListModel?.areContentsTheSame(wishListModel) } ?: false)
}