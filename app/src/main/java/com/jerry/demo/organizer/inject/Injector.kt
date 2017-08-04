package com.jerry.demo.organizer.inject

import android.app.Application
import java.util.concurrent.atomic.*

object Injector {
    lateinit var appComponent: AppComponent
    private val initialized = AtomicBoolean(false)

    fun init(app: Application) {
        if (initialized.compareAndSet(false, true)) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(AppModule(app))
                    .build()
        }
    }

    fun get(): AppComponent {
        if (!initialized.get()) {
            error("init must be called before get")
        }
        return appComponent
    }
}
