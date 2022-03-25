package com.gustavofc97.domain.repositories

import com.gustavofc97.domain.model.Comment

interface CommentsRepository {

    suspend fun getCommentsByPost(postId: String): List<Comment>

}