package com.thanosfisherman.domain.model

sealed class ErrorModel {

    // Generic network errors
    sealed class NetworkError : ErrorModel() {
        object Network : NetworkError()
        object Timeout : NetworkError()
        object ServerUnavailable : NetworkError()
    }

    // Generic HTTP Error 400+ from myGWF
    data class ServerError(
        val code: String,
        val detail: String? = null,
        val errors: Map<String, List<String>>? = null
    ) : ErrorModel()

    // Login Params Validation
    sealed class LoginError : ErrorModel() {
        object UsernameEmpty : LoginError()
        object PasswordEmpty : LoginError()
    }

    object Unknown : ErrorModel()
}
