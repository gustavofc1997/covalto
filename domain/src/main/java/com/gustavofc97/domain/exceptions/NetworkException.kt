package com.gustavofc97.domain.exceptions

sealed class NetworkException(message: String?) : Throwable(message) {
    class ServerError(message: String?) : NetworkException(message)
}