package com.gustavofc97.domain.repositories

import com.gustavofc97.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {

    suspend fun getPosts(): Flow<List<Post>>
}