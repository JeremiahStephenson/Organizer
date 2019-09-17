package com.jerry.demo.organizer.inject

import android.app.Application
import android.content.Context
import android.view.inputmethod.InputMethodManager
import com.jerry.demo.organizer.util.LifecycleWatcher
import org.koin.dsl.module

val appModule = module {

    // single instance of shared preferences
    single { get<Application>().getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE) }

    single { get<Application>().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager }

    single { LifecycleWatcher() }
}

