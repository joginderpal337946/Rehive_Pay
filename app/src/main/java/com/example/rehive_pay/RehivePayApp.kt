package com.example.rehive_pay

import android.app.Application
import com.example.rehive_pay.base.module.appModule
import com.example.rehive_pay.base.module.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class RehivePayApp : Application() {
    override fun onCreate() {
        super.onCreate()
        
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@RehivePayApp)
            modules(
                appModule,
                networkModule
            )
        }
    }
}
