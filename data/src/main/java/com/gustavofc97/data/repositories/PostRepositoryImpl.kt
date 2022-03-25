package com.gustavofc97.data.repositories

import com.gustavofc97.data.model.asDomainModel
import com.gustavofc97.data.network.NetworkClient
import com.gustavofc97.data.network.PostServices
import com.gustavofc97.domain.exceptions.PostsNotFoundException
import com.gustavofc97.domain.model.Post
import com.gustavofc97.domain.repositories.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PostRepositoryImpl(
    private val postServices: PostServices,
    private val networkClient: NetworkClient,
) : PostRepository {
    override suspend fun getPosts(): Flow<List<Post>> {
        return flow {
            val response = networkClient.apiCall(postServices.getAllPosts())
            val postListResponse = response.body() ?: throw PostsNotFoundException()
            emit( postListResponse.map {
                it.asDomainModel()
            })
        }
    }
}