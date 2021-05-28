package com.example.conduit.ui.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.api.models.entities.Article
import com.example.conduit.databinding.ListItemArticleBinding

class FeedAdapter(val listener: OnArticleClickedListener): ListAdapter<Article, FeedAdapter.ArticleViewHolder>(DiffCallback) {
    interface OnArticleClickedListener{
        fun onArticleClicked()
    }

    inner class ArticleViewHolder(private var binding: ListItemArticleBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(Article: Article){
            binding.article = Article
            binding.root.setOnClickListener{
                listener.onArticleClicked()
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(ListItemArticleBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.toString() == newItem.toString()
        }
    }

}
