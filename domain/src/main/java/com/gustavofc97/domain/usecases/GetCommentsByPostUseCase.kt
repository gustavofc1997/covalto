package com.gustavofc97.domain.usecases

import com.gustavofc97.domain.repositories.CommentsRepository

class GetCommentsByPostUseCase(private val repository: CommentsRepository) {
    suspend operator fun invoke(postId: String) = repository.getCommentsByPost(postId)
}