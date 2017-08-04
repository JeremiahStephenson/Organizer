package com.jerry.demo.organizer.util

import android.support.annotation.ColorInt
import android.support.v4.graphics.drawable.DrawableCompat
import android.view.MenuItem

fun MenuItem.tintIcon(@ColorInt colorInt: Int) {
    icon?.let {
        val wrapped = DrawableCompat.wrap(it)
        it.mutate()
        DrawableCompat.setTint(wrapped, colorInt)
        setIcon(it)
    }
}
