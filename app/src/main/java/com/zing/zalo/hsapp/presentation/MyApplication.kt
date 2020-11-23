package com.zing.zalo.hsapp.presentation

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApplication: Application() {

    companion object {
        private lateinit var instance: MyApplication

        @Synchronized
        fun getInstance() = instance
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Timber.i("create app")
        instance = this
    }

    override fun onTerminate() {
        super.onTerminate()
        Timber.i("destroy app")
    }
}