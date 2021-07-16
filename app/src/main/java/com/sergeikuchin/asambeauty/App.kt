package com.sergeikuchin.asambeauty

import android.app.Application
import com.sergeikuchin.asambeauty.servicelocator.initKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin(this)
    }
}