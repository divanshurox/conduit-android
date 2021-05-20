package com.example.conduit

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.api.models.entities.Article
import com.example.conduit.ui.feed.FeedAdapter

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Article>?){
    val adapter = recyclerView.adapter as FeedAdapter
    adapter.submitList(data)
}