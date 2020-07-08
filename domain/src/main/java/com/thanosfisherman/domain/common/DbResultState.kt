package com.thanosfisherman.domain.common


sealed class DbResultState<out T : Any> {

    object Loading : DbResultState<Nothing>()

    object ShowDialodRemove : DbResultState<Nothing>()

    object GenericError : DbResultState<Nothing>()

    object EmptyError : DbResultState<Nothing>()

    data class Success<T : Any>(val data: T) : DbResultState<T>()
}
