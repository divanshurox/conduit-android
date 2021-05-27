package com.example.conduit.data

import com.example.api.ConduitClient
import com.example.api.models.entities.LoginData
import com.example.api.models.entities.RegisterData
import com.example.api.models.entities.User
import com.example.api.models.requests.LoginRequest
import com.example.api.models.requests.RegisterRequest

object AuthRepo {
    val api = ConduitClient.publicApi
    val authApi = ConduitClient.authApi

    suspend fun login(email: String,password: String): User? {
        val response = api.loginUser(LoginRequest(LoginData(email,password)))
        ConduitClient.authToken = response.body()?.user?.token
        return response.body()?.user
    }

    suspend fun register(username: String,email: String,password: String): User? {
        val response = api.registerUser(RegisterRequest(RegisterData(email,password,username)))
        ConduitClient.authToken = response.body()?.user?.token
        return response.body()?.user
    }

    suspend fun getUserProfile() = authApi.getCurrentUser().body()?.user
}