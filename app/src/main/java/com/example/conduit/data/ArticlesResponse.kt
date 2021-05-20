package com.example.conduit.data

import com.example.api.ConduitClient

object ArticlesResponse {
    val api = ConduitClient().api

    suspend fun getGlobalFeed() = api.getArticles()
}