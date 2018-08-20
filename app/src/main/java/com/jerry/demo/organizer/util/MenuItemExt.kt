package com.jerry.demo.organizer.util

import android.support.annotation.ColorInt
import android.view.MenuItem
import androidx.core.graphics.drawable.DrawableCompat

fun MenuItem.tintIcon(@ColorInt colorInt: Int) {
    icon?.let {
        val wrapped = DrawableCompat.wrap(it)
        it.mutate()
        DrawableCompat.setTint(wrapped, colorInt)
        setIcon(it)
    }
}
