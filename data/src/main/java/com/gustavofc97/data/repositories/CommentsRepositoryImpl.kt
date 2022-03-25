package com.gustavofc97.data.repositories

import com.gustavofc97.data.model.toDomainModel
import com.gustavofc97.data.network.NetworkClient
import com.gustavofc97.data.network.PostServices
import com.gustavofc97.domain.exceptions.CommentsNotFoundException
import com.gustavofc97.domain.model.Comment
import com.gustavofc97.domain.repositories.CommentsRepository

class CommentsRepositoryImpl(
    private val postServices: PostServices,
    private val networkClient: NetworkClient
) : CommentsRepository {
    override suspend fun getCommentsByPost(postId: String): List<Comment> {
        val response = networkClient.apiCall(postServices.getPostComments(postId))
        val commentsResponse = response.body() ?: throw CommentsNotFoundException()
        return commentsResponse.map { it.toDomainModel() }
    }
}