package com.gustavofc97.data.model

import com.gustavofc97.domain.model.Comment

data class APIComment(val body: String, val name: String, val email: String)

fun APIComment.toDomainModel() = Comment(name, body, email)