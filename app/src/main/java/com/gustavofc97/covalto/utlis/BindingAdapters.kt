package com.gustavofc97.covalto.utlis

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gustavofc97.domain.model.Comment
import com.gustavofc97.domain.model.Post
import com.gustavofc97.covalto.detail.adapters.CommentsAdapter
import com.gustavofc97.covalto.home.ui.adapters.PostItemsAdapter

@BindingAdapter("comment_list")
fun createCommentsAdapter(recyclerView: RecyclerView, items: List<Comment>?) {
    recyclerView.adapter = CommentsAdapter().apply {
        submitList(items)
    }
}

@BindingAdapter("post_list")
fun createPostAdapter(recyclerView: RecyclerView, items: List<Post>?) {
    (recyclerView.adapter as? PostItemsAdapter)?.submitList(items)
}
