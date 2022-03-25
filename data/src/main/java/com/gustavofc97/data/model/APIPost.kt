package com.gustavofc97.data.model

import com.gustavofc97.domain.model.Post

data class APIPost(val title: String, val body: String, val id: Int, val userId: Int)

fun APIPost.asDomainModel() = Post(id, userId, title, body)
