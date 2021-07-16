package com.sergeikuchin.asambeauty.domain.mappers

import com.sergeikuchin.asambeauty.domain.models.CartListItemModel
import com.sergeikuchin.asambeauty.domain.models.WishListItemModel

interface WishToCartMapper {

    fun map(wishListItemModel: WishListItemModel): CartListItemModel
}