package com.gustavofc97.covalto.home.viewmodels

import androidx.databinding.ObservableField
import com.gustavofc97.domain.model.Post
import com.gustavofc97.domain.usecases.GetPostListUseCase
import com.gustavofc97.covalto.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostListViewModel @Inject constructor(
    private val getPostUseCase: GetPostListUseCase
) :
    BaseViewModel() {

    val postList = ObservableField<List<Post>>()

    fun getAllPosts() {
        launchInBackground {
            getPostUseCase().collect {
                postList.set(it)
            }
        }
    }
}