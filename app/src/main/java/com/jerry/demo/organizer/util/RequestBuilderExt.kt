package com.jerry.demo.organizer.util

import android.widget.ImageView
import androidx.lifecycle.DefaultLifecycleObserver
import coil.request.LoadRequestBuilder
import coil.target.PoolableViewTarget

fun LoadRequestBuilder.customTarget(imageView: ImageView) {
    target(TestTarget(imageView))
}

private class TestTarget(override val view: ImageView) : PoolableViewTarget<ImageView>, DefaultLifecycleObserver {
    override fun onClear() {

    }
}