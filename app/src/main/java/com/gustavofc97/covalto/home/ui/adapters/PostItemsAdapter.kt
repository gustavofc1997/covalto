package com.gustavofc97.covalto.home.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gustavofc97.covalto.databinding.LayoutItemPostBinding
import com.gustavofc97.domain.model.Post

class PostItemsAdapter : ListAdapter<Post, RecyclerView.ViewHolder>(PostsDiffCallBack()) {

    private var onItemSelection: ((Post) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PostItemViewHolder(
            LayoutItemPostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PostItemViewHolder).bind(getItem(position))
    }

    private class PostsDiffCallBack : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean =
            oldItem == newItem
    }

    inner class PostItemViewHolder(private val binding: LayoutItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Post) {
            binding.tvPostDescription.text = item.title
            itemView.setOnClickListener { onItemSelection?.invoke(item) }
        }
    }

    fun setOnPostClicked(block: ((Post) -> Unit)) {
        this.onItemSelection = block
    }
}

