package com.example.geoffrey.bibliotheekapp.network
import com.example.geoffrey.bibliotheekapp.models.Book
import com.example.geoffrey.bibliotheekapp.models.ExpandableShopList
import com.example.geoffrey.bibliotheekapp.models.ShopList
import com.example.geoffrey.bibliotheekapp.models.User
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL = "http://10.0.2.2:3000/api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
    this.level = HttpLoggingInterceptor.Level.BODY
}
val client:OkHttpClient = OkHttpClient().newBuilder().apply {
    this.addInterceptor(interceptor)
}.build()

private val retrofit = Retrofit.Builder()
    .client(client)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface BookApiService {
    @POST("users/login")
    fun login(@Body user:User): Deferred<ResponseBody>

    @POST("users/register")
    fun register(@Body user: User): Deferred<Response<ResponseBody>>

    @POST("users/checkusername")
    fun checkUsername(@Body username:User):Deferred<Response<User>>

    @GET("boeken")
    fun getBooks ():Deferred<List<Book>>

    @GET("boek/{id}")
    fun getBookById(@Path("id") id:String? ) : Deferred<Book>

    @POST("winkelwagen")
    fun saveReservationsToDatabase(@Body shopList:List<Book>?, @Header("Authorization") token: String):Deferred<ResponseBody>

    @GET("winkelwagen")
    fun getShopList():Deferred<List<ExpandableShopList>>

    @GET("winkelwagen")
    fun getSList():Call<Array<ExpandableShopList>>

    @GET("boekWinkelwagentje/{id}")
    fun getBookByObjectId(@Path("id") id:String) : Deferred<Book>

    @GET("users/userById/{id}")
    fun getUserByObjectId(@Path("id") id:String):Deferred<User>
}

/**
 * A BookApi object is created and implements retrofitService by lazy (is only called once) which creates a retrofitservice from BookApiService
 */
object BookApi {
    val retrofitService: BookApiService by lazy {
        retrofit.create(BookApiService::class.java)
    }
}