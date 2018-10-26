package com.jerry.demo.organizer.inject

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.view.inputmethod.InputMethodManager
import com.jerry.demo.organizer.util.LifecycleWatcher
import org.koin.dsl.module.module

val appModule = module {

    // single instance of shared preferences
    single<SharedPreferences> { PreferenceManager.getDefaultSharedPreferences(get()) }

    single { get<Application>().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager }

    single { LifecycleWatcher() }
}

