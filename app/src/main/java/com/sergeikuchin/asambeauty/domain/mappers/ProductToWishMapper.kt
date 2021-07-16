package com.sergeikuchin.asambeauty.domain.mappers

import com.sergeikuchin.asambeauty.domain.models.ProductModel
import com.sergeikuchin.asambeauty.domain.models.WishListItemModel

interface ProductToWishMapper {

    fun map(
        productModel: ProductModel,
        wishListItemModel: WishListItemModel?,
    ): WishListItemModel?
}