package com.jerry.demo.organizer

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import com.jerry.demo.organizer.inject.Injector
import com.jerry.demo.organizer.util.LifecycleWatcher
import javax.inject.Inject

class App : Application() {
    @Inject
    lateinit var lifecycleWatcher: LifecycleWatcher

    override fun onCreate() {
        super.onCreate()
        Injector.init(this)
        Injector.get().inject(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(lifecycleWatcher)
    }
}
