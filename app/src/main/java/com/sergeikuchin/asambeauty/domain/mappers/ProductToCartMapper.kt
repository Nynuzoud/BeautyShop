package com.sergeikuchin.asambeauty.domain.mappers

import com.sergeikuchin.asambeauty.domain.models.CartListItemModel
import com.sergeikuchin.asambeauty.domain.models.ProductModel

interface ProductToCartMapper {

    fun map(
        productModel: ProductModel,
        cartListItemModel: CartListItemModel?
    ): CartListItemModel?
}