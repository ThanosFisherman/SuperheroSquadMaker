package com.thanosfisherman.domain.common

abstract class BaseUseCase<in Params, out Type> {

    abstract fun execute(params: Params): Type

    object None
}
