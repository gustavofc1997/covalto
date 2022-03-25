package com.gustavofc97.domain.usecases

import com.gustavofc97.domain.repositories.PostRepository

class GetPostListUseCase(private val postRepository: PostRepository) {
    suspend operator fun invoke() = postRepository.getPosts()
}