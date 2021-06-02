package com.example.conduit

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.api.models.entities.Article
import com.example.conduit.ui.feed.FeedAdapter

@BindingAdapter("imgUrl")
fun bindImage(imageView: ImageView, imgUrl: String?){
    imgUrl?.let{
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        imageView.load(imgUri){
            transformations(listOf(CircleCropTransformation()))
        }
    }
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Article>?){
    val adapter = recyclerView.adapter as FeedAdapter
    adapter.submitList(data)
}
