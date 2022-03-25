package com.gustavofc97.covalto.detail.viewmodel

import androidx.databinding.ObservableField
import com.gustavofc97.domain.model.Comment
import com.gustavofc97.domain.model.Post
import com.gustavofc97.domain.model.User
import com.gustavofc97.domain.usecases.GetCommentsByPostUseCase
import com.gustavofc97.domain.usecases.GetUserByIdUseCase
import com.gustavofc97.covalto.base.BaseViewModel
import com.gustavofc97.covalto.utlis.EMPTY_STRING
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val getCommentsUseCase: GetCommentsByPostUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase,
) : BaseViewModel() {

    val userName = ObservableField(EMPTY_STRING)
    val userPhone = ObservableField(EMPTY_STRING)
    val userWebsite = ObservableField(EMPTY_STRING)
    val userEmail = ObservableField(EMPTY_STRING)
    val postDescription = ObservableField(EMPTY_STRING)
    val commentList = ObservableField<List<Comment>>()

    private var postItem: Post? = null
        set(value) {
            field = value
            loadPostDetails(postItem?.userId, postItem?.id)
        }

    fun setUpPostItem(post: Post) {
        this.postItem = post
        postDescription.set(post.description)
    }

    private fun loadPostDetails(userId: Int?, postId: Int?) {
        launchInBackground {
            val user = getUserByIdUseCase(userId.toString())
            setUp(user)
            val comments = getCommentsUseCase(postId.toString())
            commentList.set(comments)
        }
    }

    private fun setUp(user: User) {
        userName.set(user.name)
        userPhone.set(user.phone)
        userWebsite.set(user.website)
        userEmail.set(user.email)
    }
}