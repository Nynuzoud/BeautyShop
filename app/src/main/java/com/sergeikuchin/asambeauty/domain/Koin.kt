package com.sergeikuchin.asambeauty.domain

import com.sergeikuchin.asambeauty.domain.delegates.HomeViewModelDelegate
import com.sergeikuchin.asambeauty.domain.delegates.HomeViewModelDelegateImpl
import com.sergeikuchin.asambeauty.domain.mappers.*
import com.sergeikuchin.asambeauty.domain.usecases.GetHomeListUseCase
import com.sergeikuchin.asambeauty.domain.usecases.GetHomeListUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module {

    factory<GetHomeListUseCase> { GetHomeListUseCaseImpl() }
    factory<ProductToWishMapper> { ProductToWishMapperImpl() }
    factory<ProductToCartMapper> { ProductToCartMapperImpl() }
    factory<WishToCartMapper> { WishToCartMapperImpl() }
    factory<HomeViewModelDelegate> {
        HomeViewModelDelegateImpl(
            productToWishMapper = get(),
            wishToCartMapper = get(),
            productToCartMapper = get()
        )
    }
}