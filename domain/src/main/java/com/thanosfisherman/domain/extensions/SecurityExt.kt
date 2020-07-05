package com.thanosfisherman.domain.extensions

import java.security.MessageDigest

/**
 * Extension function creates a MD5 digest from the receiver String
 *
 * @receiver String from witch we want a MD5 digest.
 * @return MD5 digest string.
 */
fun String.toMD5(): String {
    val md = MessageDigest.getInstance("MD5")
    val digest = md.digest(toByteArray())
    return digest.joinToString("") {
        String.format("%02x", it)
    }
}