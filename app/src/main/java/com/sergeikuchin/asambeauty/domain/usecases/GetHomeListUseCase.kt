package com.sergeikuchin.asambeauty.domain.usecases

import com.sergeikuchin.asambeauty.domain.ResultStatus
import com.sergeikuchin.asambeauty.domain.UseCase
import com.sergeikuchin.asambeauty.domain.models.CartWishListModel
import com.sergeikuchin.asambeauty.domain.models.ProductModel
import io.reactivex.rxjava3.core.Observable

interface GetHomeListUseCase {

    fun getHomeList(): Observable<GetHomeListResult>
}

object GetHomeListQuery : UseCase.Query

data class GetHomeListResult(
    val cartWishListModel: CartWishListModel?,
    val productModels: List<ProductModel>,
    override val status: ResultStatus
) : UseCase.Result