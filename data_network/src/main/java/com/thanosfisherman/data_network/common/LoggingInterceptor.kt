package com.thanosfisherman.data_network.common

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor

internal class LoggingInterceptor : Interceptor {

    private val httpLoggingInterceptor = HttpLoggingInterceptor()

    init {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    }

    override fun intercept(chain: Interceptor.Chain): Response =
        httpLoggingInterceptor.intercept(chain)
}
