package com.jerry.demo.organizer.util

import android.content.Context
import android.view.Menu
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun Menu.tintAllIcons(context: Context, @ColorRes colorResId: Int) {
    for (index in 0 until size()) {
        getItem(index).tintIcon(ContextCompat.getColor(context, colorResId))
    }
}
