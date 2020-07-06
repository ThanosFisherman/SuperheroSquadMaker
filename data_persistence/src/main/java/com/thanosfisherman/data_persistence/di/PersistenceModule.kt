package com.thanosfisherman.data_persistence.di

import com.thanosfisherman.data_persistence.db.HeroesDatabase
import com.thanosfisherman.data_persistence.repos.DbRepoImpl
import com.thanosfisherman.domain.repos.DbRepo
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val persistenceModule = module {

    single { HeroesDatabase.getInstance(androidContext()) }
    single { get<HeroesDatabase>().heroesDao() }
    single { get<HeroesDatabase>().squadDao() }
    single<DbRepo> { DbRepoImpl(get(), get()) }
}
