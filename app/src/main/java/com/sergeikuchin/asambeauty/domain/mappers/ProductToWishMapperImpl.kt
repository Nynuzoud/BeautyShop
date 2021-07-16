package com.sergeikuchin.asambeauty.domain.mappers

import com.sergeikuchin.asambeauty.domain.models.ProductModel
import com.sergeikuchin.asambeauty.domain.models.WishListItemModel

class ProductToWishMapperImpl : ProductToWishMapper {

    override fun map(
        productModel: ProductModel,
        wishListItemModel: WishListItemModel?
    ): WishListItemModel? {
        return WishListItemModel(
            id = productModel.id,
            title = productModel.title,
            count = ((wishListItemModel?.count ?: 0) + 1).takeIf { it <= 3 } ?: return null,
            image = productModel.image,
            price = productModel.price,
            oldPrice = productModel.oldPrice
        )
    }
}