package com.example.api

import com.example.api.services.ConduitAPI
import com.example.api.services.ConduitAuthAPI
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object ConduitClient {
    var authToken: String? = null

    private val authInterceptor = Interceptor { chain ->
        var req = chain.request()
        authToken?.let{
            req = req.newBuilder().header("Authorization","Token $it").build()
        }
        chain.proceed(req)
    }

    private val baseUrl = "https://conduit.productionready.io/api/"
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val okhttpBuilder = OkHttpClient.Builder()
        .readTimeout(5,TimeUnit.SECONDS)
        .connectTimeout(2,TimeUnit.SECONDS)

    private val retrofitBuilder = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(baseUrl)

    val publicApi = retrofitBuilder
        .client(okhttpBuilder.build())
        .build()
        .create(ConduitAPI::class.java)

    val authApi = retrofitBuilder
        .client(okhttpBuilder.addInterceptor(authInterceptor).build())
        .build()
        .create(ConduitAuthAPI::class.java)
}