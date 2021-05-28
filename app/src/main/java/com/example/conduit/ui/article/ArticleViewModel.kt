package com.example.conduit.ui.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.api.models.entities.Article

class ArticleViewModel: ViewModel() {
    private val _article = MutableLiveData<Article>()
    val article: LiveData<Article> get() = _article


}