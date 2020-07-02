package com.thanosfisherman.superherosquadmaker

import android.app.Application
import timber.log.Timber

class SquadMakerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initTimber()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}