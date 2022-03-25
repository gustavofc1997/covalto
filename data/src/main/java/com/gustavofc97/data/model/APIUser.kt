package com.gustavofc97.data.model

import com.gustavofc97.domain.model.User

data class APIUser(val name: String, val website: String, val email: String, val phone: String)

fun APIUser.toDomainModel() = User(name, email, phone, website)