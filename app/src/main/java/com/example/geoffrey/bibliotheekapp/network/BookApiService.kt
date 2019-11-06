package com.example.geoffrey.bibliotheekapp.network

import com.example.geoffrey.bibliotheekapp.models.User
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

private const val BASE_URL = "http://10.0.2.2:3000/api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface BookApiService {
    @POST("users/login")
    fun login(@Body user:User): Call<User>

    @POST("users/register")
    fun register(@Body user: User): Call<User>

    @POST("users/checkusername")
    fun checkUsername(@Body username:User):Call<User>
}

object BookApi {
    val retrofitService: BookApiService by lazy {
        retrofit.create(BookApiService::class.java)
    }
}