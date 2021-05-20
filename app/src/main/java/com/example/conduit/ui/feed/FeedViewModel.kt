package com.example.conduit.ui.feed

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api.models.entities.Article
import com.example.conduit.data.ArticlesResponse
import kotlinx.coroutines.launch

class FeedViewModel: ViewModel() {
    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>> get() = _articles

    fun fetchGlobalFeed(){
        viewModelScope.launch{
            ArticlesResponse.getGlobalFeed().body()?.let{
                _articles.value = it.articles
                Log.d("FeedViewModel","feed fetched, ${it.articles?.size}")
            }
        }
    }

}