package com.thanosfisherman.domain.di

import com.thanosfisherman.domain.usecase.GetAllCharactersUseCase
import org.koin.dsl.module

val domainModule = module {
    single { GetAllCharactersUseCase(get()) }
}
