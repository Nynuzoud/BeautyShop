package com.sergeikuchin.asambeauty.view

import com.sergeikuchin.asambeauty.view.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeActivityModule = module {

    viewModel {
        HomeViewModel(
            getHomeListUseCase = get(),
            homeViewModelDelegate = get()
        )
    }
}