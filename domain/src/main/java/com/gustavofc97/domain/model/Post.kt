package com.gustavofc97.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(
    val id: Int,
    val userId: Int,
    val title: String,
    val description: String,
) : Parcelable
