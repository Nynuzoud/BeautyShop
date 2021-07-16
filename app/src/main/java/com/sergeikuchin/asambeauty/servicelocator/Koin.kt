package com.sergeikuchin.asambeauty.servicelocator

import android.content.Context
import com.sergeikuchin.asambeauty.domain.useCaseModule
import com.sergeikuchin.asambeauty.view.homeActivityModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

fun initKoin(appContext: Context) {
    startKoin {

        androidContext(appContext)

        modules(listOf(
            useCaseModule,
            homeActivityModule
        ))
    }
}