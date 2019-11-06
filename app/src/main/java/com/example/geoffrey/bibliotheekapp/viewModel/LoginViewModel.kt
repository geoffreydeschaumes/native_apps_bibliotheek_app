package com.example.geoffrey.bibliotheekapp.viewModel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.geoffrey.bibliotheekapp.models.User
import com.example.geoffrey.bibliotheekapp.network.BookApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel: ViewModel() {
    var username = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    private val _response = MutableLiveData<String>()

    init {
        username.value = ""
        password.value = ""
    }

    private fun loginUser() {
        val user = User(username.value.toString(), password.value.toString())
        BookApi.retrofitService.login(user).enqueue(object: Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                _response.value = "Failure" + t.message
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {

            }

        })
    }

}