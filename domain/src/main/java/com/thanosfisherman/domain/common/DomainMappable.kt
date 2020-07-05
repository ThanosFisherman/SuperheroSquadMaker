package com.thanosfisherman.domain.common

interface DomainMappable<R> {
    fun asDomain(): R
}
