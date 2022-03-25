package com.gustavofc97.data.repositories

import com.gustavofc97.data.model.toDomainModel
import com.gustavofc97.data.network.NetworkClient
import com.gustavofc97.data.network.PostServices
import com.gustavofc97.domain.exceptions.UserNotFoundException
import com.gustavofc97.domain.model.User
import com.gustavofc97.domain.repositories.UserRepository

class UserRepositoryImpl(
    private val postServices: PostServices,
    private val networkClient: NetworkClient
) : UserRepository {

    override suspend fun getUserById(userId: String): User {
        val response = networkClient.apiCall(postServices.getUser(userId))
        val apiUser = response.body() ?: throw UserNotFoundException()
        return apiUser.toDomainModel()
    }
}