package com.example.geoffrey.bibliotheekapp.repositories

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.geoffrey.bibliotheekapp.database.BookDatabase
import com.example.geoffrey.bibliotheekapp.database.BookDatabaseDao_Impl
import com.example.geoffrey.bibliotheekapp.models.User
import com.example.geoffrey.bibliotheekapp.network.BookApi
import kotlinx.coroutines.*
import okhttp3.ResponseBody
import okhttp3.internal.http.RealResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class UserRepository{

    suspend fun login(user:User):ResponseBody {
            return BookApi.retrofitService.login(user).await()
    }

    suspend fun registrate(user:User) : ResponseBody {

        return BookApi.retrofitService.register(user).await()
    }

    suspend fun checkUsername(user:User):ResponseBody{
        return BookApi.retrofitService.checkUsername(user).await()
    }
}