package com.jerry.demo.organizer

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import coil.Coil
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.util.CoilUtils
import com.jerry.demo.organizer.database.databaseModule
import com.jerry.demo.organizer.inject.appModule
import com.jerry.demo.organizer.inject.viewModelModule
import com.jerry.demo.organizer.util.LifecycleWatcher
import okhttp3.OkHttpClient
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    private val lifecycleWatcher by inject<LifecycleWatcher>()

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(level = Level.ERROR)
            androidContext(this@App)
            modules(listOf(appModule, databaseModule, viewModelModule))
        }
        ProcessLifecycleOwner.get().lifecycle.addObserver(lifecycleWatcher)

        Coil.setImageLoader(object : ImageLoaderFactory {
            override fun newImageLoader(): ImageLoader {
                return ImageLoader.Builder(this@App)
                        .crossfade(false)
                        .okHttpClient {
                            OkHttpClient.Builder()
                                    .cache(CoilUtils.createDefaultCache(this@App))
                                    .build()
                        }.build()
            }
        })
    }
}
