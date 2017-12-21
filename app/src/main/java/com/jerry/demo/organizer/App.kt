package com.jerry.demo.organizer

import android.app.Application
import android.arch.lifecycle.ProcessLifecycleOwner
import com.cloudinary.android.MediaManager
import com.jerry.demo.organizer.inject.Injector
import com.jerry.demo.organizer.util.LifecycleWatcher
import javax.inject.Inject

class App : Application() {
    @Inject
    lateinit var lifecycleWatcher: LifecycleWatcher

    override fun onCreate() {
        super.onCreate()
        MediaManager.init(this)
        Injector.init(this)
        Injector.get().inject(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(lifecycleWatcher)
    }
}
