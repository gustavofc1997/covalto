package com.gustavofc97.domain.usecases

import com.gustavofc97.domain.repositories.UserRepository

class GetUserByIdUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(userId: String) = repository.getUserById(userId)
}