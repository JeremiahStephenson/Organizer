package com.jerry.demo.organizer.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jerry.demo.organizer.inject.Injector
import com.jerry.demo.organizer.util.LifecycleWatcher
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {
    @Inject
    lateinit var lifecycleWatcher: LifecycleWatcher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.get().inject(this)
        lifecycle.addObserver(lifecycleWatcher)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(lifecycleWatcher)
    }
}