package com.yurikolesnikov.bloomus

import android.app.Application
import com.yurikolesnikov.network.authentication.FirebaseAuthenticationManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BloomUsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseAuthenticationManager.initialize(this)
    }
}