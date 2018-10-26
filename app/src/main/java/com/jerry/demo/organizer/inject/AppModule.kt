package com.jerry.demo.organizer.inject

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.view.inputmethod.InputMethodManager
import com.jerry.demo.organizer.database.DatabaseModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = arrayOf(DatabaseModule::class))
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    internal fun provideApplication() = application

    @Provides
    fun provideSharedPreferences(application: Application): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application)
    }

    @Provides
    fun provideInputMethodManager(application: Application): InputMethodManager {
        return application.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }
}
