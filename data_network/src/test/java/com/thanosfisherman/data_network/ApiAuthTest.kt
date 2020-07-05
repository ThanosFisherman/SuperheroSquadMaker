package com.thanosfisherman.data_network

import com.thanosfisherman.domain.extensions.toMD5
import org.junit.Assert.assertEquals
import org.junit.Test

class ApiAuthTest {
    private val PUBLIC_KEY = "ff8b08af878218efbb787bdd7b4ce4be"
    private val PRIVATE_KEY = "e4f4f931fb700bc18ae324958555c4eb5fde60ab"

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun createAuthKey() {
        val timestamp = System.currentTimeMillis().toString()
        println("timestamp $timestamp")
        val result = timestamp + PRIVATE_KEY + PUBLIC_KEY
        println(result.toMD5())
    }
}