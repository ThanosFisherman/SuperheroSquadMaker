package com.thanosfisherman.data_network.common

import com.thanosfisherman.domain.extensions.toMD5
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    private val PUBLIC_KEY = "ff8b08af878218efbb787bdd7b4ce4be"
    private val PRIVATE_KEY = "e4f4f931fb700bc18ae324958555c4eb5fde60ab"

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val timestamp = System.currentTimeMillis().toString()
        val hash = timestamp + PRIVATE_KEY + PUBLIC_KEY
        val url = original.url.newBuilder().addQueryParameter("apikey", PUBLIC_KEY)
            .addQueryParameter("ts", timestamp).addQueryParameter("hash", hash.toMD5()).build()
        val request = original.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}
