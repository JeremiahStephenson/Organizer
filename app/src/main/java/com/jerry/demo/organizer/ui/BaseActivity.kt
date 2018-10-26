package com.jerry.demo.organizer.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jerry.demo.organizer.util.LifecycleWatcher
import org.koin.android.ext.android.inject

abstract class BaseActivity : AppCompatActivity() {
    private val lifecycleWatcher by inject<LifecycleWatcher>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(lifecycleWatcher)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(lifecycleWatcher)
    }
}