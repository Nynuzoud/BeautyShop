package com.sergeikuchin.asambeauty.domain.delegates

import com.sergeikuchin.asambeauty.domain.models.CartWishListModel
import com.sergeikuchin.asambeauty.domain.models.ProductModel

interface HomeViewModelDelegate {

    fun addProductToWishList(
        productId: Long,
        cartWishListModel: CartWishListModel?,
        products: List<ProductModel>
    ): Pair<CartWishListModel?, List<ProductModel>>

    fun addProductToCartList(
        productId: Long,
        cartWishListModel: CartWishListModel?,
        products: List<ProductModel>
    ): Pair<CartWishListModel?, List<ProductModel>>

    fun addWishToCartList(
        productId: Long,
        cartWishListModel: CartWishListModel?,
        products: List<ProductModel>
    ): Pair<CartWishListModel?, List<ProductModel>>

    fun removeFromCart(
        productId: Long,
        cartWishListModel: CartWishListModel?,
        products: List<ProductModel>
    ): Pair<CartWishListModel?, List<ProductModel>>

    fun changeWishProductCount(
        productId: Long,
        count: Int,
        cartWishListModel: CartWishListModel?
    ): CartWishListModel?

    fun changeCartProductCount(
        productId: Long,
        count: Int,
        cartWishListModel: CartWishListModel?
    ): CartWishListModel?
}