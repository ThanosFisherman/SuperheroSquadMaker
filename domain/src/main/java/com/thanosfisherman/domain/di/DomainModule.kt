package com.thanosfisherman.domain.di

import com.thanosfisherman.domain.usecase.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val domainModule = module {
    single { GetAllCharactersApiUseCase(get()) }
    single { GetAllCharactersDbUseCase(get()) }
    single { GetSquadUseCase(get()) }
    single { DisplayComicsUseCase(get()) }
    single { SquadManagerUseCase(get()) }
}
