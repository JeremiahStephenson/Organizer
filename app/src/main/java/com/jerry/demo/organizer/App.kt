package com.jerry.demo.organizer

import android.app.Application
import com.jerry.demo.organizer.inject.Injector

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Injector.init(this)
    }
}
