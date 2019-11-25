package com.example.geoffrey.bibliotheekapp.network

import android.arch.lifecycle.MutableLiveData
import com.example.geoffrey.bibliotheekapp.models.Book
import com.example.geoffrey.bibliotheekapp.models.User
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.ResponseBody
import retrofit2.Call

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

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
    fun login(@Body user:User): Call<ResponseBody>

    @POST("users/register")
    fun register(@Body user: User): Call<ResponseBody>

    @POST("users/checkusername")
    fun checkUsername(@Body username:User):Call<ResponseBody>

    @GET("boeken")
    fun getBooks ():Call<List<Book>>

    @GET("boek/{id}")
    fun getBookById(@Path("id") id:String? ) : Call<Book>
}

object BookApi {
    val retrofitService: BookApiService by lazy {
        retrofit.create(BookApiService::class.java)
    }
}