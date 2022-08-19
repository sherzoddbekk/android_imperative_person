package com.example.android_imperative

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp//app classni hiltga tanish
class ImperativeApplication:Application() {

    override fun onCreate() {
        super.onCreate()
    }
}