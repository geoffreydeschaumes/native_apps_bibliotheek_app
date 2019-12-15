package com.example.geoffrey.bibliotheekapp.repositories

import android.util.Log
import com.example.geoffrey.bibliotheekapp.database.BookDatabase
import com.example.geoffrey.bibliotheekapp.models.User
import com.example.geoffrey.bibliotheekapp.network.BookApi
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository{
    fun login(user:User):String {
        var loggedInComment = ""
        BookApi.retrofitService.login(user).enqueue(object: Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                loggedInComment = "" + t.message
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                   if(response.code() != 200) {
                       loggedInComment = "User does not exist"
                   } else {
                       ""
                   }
            }

        })
        return loggedInComment
    }
}