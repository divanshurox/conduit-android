package com.example.conduit.data

import com.example.api.ConduitClient

object ArticlesRepo {
    val api = ConduitClient.publicApi
    val authApi = ConduitClient.authApi

    suspend fun getGlobalFeed() = api.getArticles().body()?.articles

    suspend fun getUserFeed() = authApi.getFeedArticles().body()?.articles

    suspend fun getArticle(slug: String) = api.getArticle(slug).body()?.article
}