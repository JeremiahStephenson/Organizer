package com.jerry.demo.organizer.util

import android.arch.lifecycle.*
import javax.inject.Inject
import javax.inject.Singleton
import android.util.Log


@Singleton
class LifecycleWatcher @Inject constructor() : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(source: LifecycleOwner) {
        Log.d("LifeCycleTest", "Activity Create")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart(source: LifecycleOwner) {
        if (source is ProcessLifecycleOwner) {
            return
        }
        Log.d("LifeCycleTest", "Activity Start")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop(source: LifecycleOwner) {
        if (source is ProcessLifecycleOwner) {
            return
        }
        Log.d("LifeCycleTest", "Activity Stop")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume(source: LifecycleOwner) {
        when (source) {
            is ProcessLifecycleOwner -> { Log.d("LifeCycleTest", "App In Foreground") }
            else -> { Log.d("LifeCycleTest", "Activity Resume") }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause(source: LifecycleOwner) {
        when (source) {
            is ProcessLifecycleOwner -> { Log.d("LifeCycleTest", "App In Background") }
            else -> { Log.d("LifeCycleTest", "Activity Pause") }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(source: LifecycleOwner) {
        Log.d("LifeCycleTest", "Activity Destroy")
    }
}