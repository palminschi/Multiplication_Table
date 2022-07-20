package com.palmdev.learn_math

import android.app.Application
import com.palmdev.learn_math.di.dataModule
import com.palmdev.learn_math.di.databaseModule
import com.palmdev.learn_math.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    presentationModule,
                    dataModule,
                    databaseModule
                )
            )
        }

    }

}