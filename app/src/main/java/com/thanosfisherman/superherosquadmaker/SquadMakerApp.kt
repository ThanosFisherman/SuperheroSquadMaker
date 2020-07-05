package com.thanosfisherman.superherosquadmaker

import android.app.Application
import com.thanosfisherman.data_network.di.networkingModule
import com.thanosfisherman.data_network.di.networkingRepoModule
import com.thanosfisherman.data_persistence.di.persistenceModule
import com.thanosfisherman.domain.di.domainModule
import com.thanosfisherman.presentation.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class SquadMakerApp : Application() {

    private val presentationModules = listOf(presentationModule)
    private val domainModules = listOf(domainModule)
    private val persistenceModules = listOf(persistenceModule)
    private val networkModules = listOf(networkingModule, networkingRepoModule)

    override fun onCreate() {
        super.onCreate()
        initKoin()
        initTimber()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@SquadMakerApp)
            if (BuildConfig.DEBUG) androidLogger(Level.DEBUG)
            modules(presentationModules + domainModules + networkModules + persistenceModules)
        }
    }
}