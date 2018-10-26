package com.jerry.demo.organizer

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import com.jerry.demo.organizer.database.databaseModule
import com.jerry.demo.organizer.inject.appModule
import com.jerry.demo.organizer.inject.viewModelModule
import com.jerry.demo.organizer.util.LifecycleWatcher
import org.koin.android.ext.android.inject
import org.koin.android.ext.android.startKoin

class App : Application() {
    private val lifecycleWatcher by inject<LifecycleWatcher>()

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule, databaseModule, viewModelModule))
        ProcessLifecycleOwner.get().lifecycle.addObserver(lifecycleWatcher)
    }
}
