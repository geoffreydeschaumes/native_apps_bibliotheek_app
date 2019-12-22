package com.example.geoffrey.bibliotheekapp.network
import com.example.geoffrey.bibliotheekapp.models.Book
import com.example.geoffrey.bibliotheekapp.models.User
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL = "http://10.0.2.2:3000/api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface BookApiService {
    @POST("users/login")
    fun login(@Body user:User): Deferred<ResponseBody>

    @POST("users/register")
    fun register(@Body user: User): Deferred<ResponseBody>

    @POST("users/checkusername")
    fun checkUsername(@Body username:User):Deferred<ResponseBody>

    @GET("boeken")
    fun getBooks ():Deferred<List<Book>>

    @GET("boek/{id}")
    fun getBookById(@Path("id") id:String? ) : Deferred<Book>

    @POST("winkelwagen")
    fun saveReservationsToDatabase(@Body shopList:List<Book>?, @Header("Authorization") token: String):Deferred<ResponseBody>
}

object BookApi {
    val retrofitService: BookApiService by lazy {
        retrofit.create(BookApiService::class.java)
    }
}