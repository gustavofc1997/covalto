package com.gustavofc97.data.network

import com.gustavofc97.domain.exceptions.NetworkException
import retrofit2.Response

class NetworkClientImpl : NetworkClient {

    override suspend fun <T : Any> apiCall(call: Response<T>): Response<T> {

        if (call.isSuccessful) {
            return call
        }
        throw NetworkException.ServerError("Web service error")
    }

}