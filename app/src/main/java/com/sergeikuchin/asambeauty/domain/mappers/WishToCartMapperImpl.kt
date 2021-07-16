package com.sergeikuchin.asambeauty.domain.mappers

import com.sergeikuchin.asambeauty.domain.models.CartListItemModel
import com.sergeikuchin.asambeauty.domain.models.WishListItemModel

class WishToCartMapperImpl : WishToCartMapper {

    override fun map(wishListItemModel: WishListItemModel): CartListItemModel =
        with(wishListItemModel) {
            CartListItemModel(
                id = id,
                title = title,
                count = count,
                price = price,
                image = image,
                oldPrice = oldPrice
            )
        }
}