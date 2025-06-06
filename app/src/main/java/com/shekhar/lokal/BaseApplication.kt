package com.shekhar.lokal

import android.app.Application
import com.shekhar.lokal.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BaseApplication)
            modules(appModule)
        }
    }
}