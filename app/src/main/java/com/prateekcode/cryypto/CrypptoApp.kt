package com.prateekcode.cryypto

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@HiltAndroidApp
class CrypptoApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}