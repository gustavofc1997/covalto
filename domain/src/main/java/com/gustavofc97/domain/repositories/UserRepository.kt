package com.gustavofc97.domain.repositories

import com.gustavofc97.domain.model.User

interface UserRepository {

    suspend fun getUserById(userId: String): User

}