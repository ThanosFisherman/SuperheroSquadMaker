package com.thanosfisherman.data_network.di

import com.thanosfisherman.data_network.api.MarvelApi
import com.thanosfisherman.data_network.repos.NetworkRepoImpl
import com.thanosfisherman.domain.repos.NetworkRepo
import org.koin.dsl.module
import retrofit2.Retrofit

val networkingRepoModule = module {

    single { get<Retrofit>().create(MarvelApi::class.java) }
    single<NetworkRepo> { NetworkRepoImpl(get()) }
}
