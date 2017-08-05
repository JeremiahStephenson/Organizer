package com.jerry.demo.organizer.util

import android.content.Context
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.view.Menu

fun Menu.tintAllIcons(context: Context, @ColorRes colorResId: Int) {
    for (index in 0 until size()) {
        getItem(index).tintIcon(ContextCompat.getColor(context, colorResId))
    }
}
