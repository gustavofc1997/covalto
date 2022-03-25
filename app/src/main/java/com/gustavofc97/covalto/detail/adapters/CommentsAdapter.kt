package com.gustavofc97.covalto.detail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gustavofc97.covalto.databinding.LayoutItemCommentBinding
import com.gustavofc97.domain.model.Comment

class CommentsAdapter : ListAdapter<Comment, RecyclerView.ViewHolder>(CommentDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CommentsViewHolder(
            LayoutItemCommentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CommentsViewHolder).bind(getItem(position))
    }

    inner class CommentsViewHolder(private val binding: LayoutItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Comment) {
            binding.tvComment.text = item.title
        }
    }

    private class CommentDiffCallBack : DiffUtil.ItemCallback<Comment>() {
        override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean =
            oldItem == newItem
    }

}