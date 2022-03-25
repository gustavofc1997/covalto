package com.gustavofc97.data.network

import retrofit2.Response

interface NetworkClient {

    suspend fun <T : Any> apiCall(call: Response<T>): Response<T>

}