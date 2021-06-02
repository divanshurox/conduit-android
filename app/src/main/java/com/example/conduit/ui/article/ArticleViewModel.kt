package com.example.conduit.ui.article

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api.models.entities.Article
import com.example.conduit.data.ArticlesRepo
import kotlinx.coroutines.launch

class ArticleViewModel: ViewModel() {
    private val _article = MutableLiveData<Article>()
    val article: LiveData<Article> get() = _article

    fun getArticle(slug: String){
        try {
            viewModelScope.launch{
                ArticlesRepo.getArticle(slug)?.let{
                    _article.value = it
                }
            }
        }catch (e: Exception){
            Log.d("ArticleViewModel","${e.message}")
        }
    }
}