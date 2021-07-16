package com.sergeikuchin.asambeauty.domain.mappers

import com.sergeikuchin.asambeauty.domain.models.CartListItemModel
import com.sergeikuchin.asambeauty.domain.models.ProductModel

class ProductToCartMapperImpl : ProductToCartMapper {

    override fun map(
        productModel: ProductModel,
        cartListItemModel: CartListItemModel?,
    ): CartListItemModel? {
        with(productModel) {
            return CartListItemModel(
                id = id,
                title = title,
                count = ((cartListItemModel?.count ?: 0) + 1).takeIf { it <= 3 } ?: return null,
                price = price,
                image = image,
                oldPrice = oldPrice
            )
        }
    }
}