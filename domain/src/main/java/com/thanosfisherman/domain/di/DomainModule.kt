package com.thanosfisherman.domain.di

import com.thanosfisherman.domain.usecase.GetAllCharactersApiUseCase
import com.thanosfisherman.domain.usecase.GetAllCharactersDbUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val domainModule = module {
    single { GetAllCharactersApiUseCase(get()) }
    single { GetAllCharactersDbUseCase(get()) }
}
