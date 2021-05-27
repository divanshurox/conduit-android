package com.example.api.models.requests


import com.example.api.models.entities.RegisterData
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegisterRequest(
    @Json(name = "user")
    val user: RegisterData
)