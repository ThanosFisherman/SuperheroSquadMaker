package com.thanosfisherman.domain.common

import org.koin.core.KoinComponent

abstract class BaseUseCase<in Params, out Type> : KoinComponent {

    abstract fun execute(params: Params): Type

    object None
}

