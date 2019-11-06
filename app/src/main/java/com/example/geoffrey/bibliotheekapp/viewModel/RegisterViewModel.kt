package com.example.geoffrey.bibliotheekapp.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.util.Log
import com.example.geoffrey.bibliotheekapp.models.User
import com.example.geoffrey.bibliotheekapp.network.BookApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

public class RegisterViewModel:  ViewModel() {

    @Bindable
    var _username = MutableLiveData<String>()
    val username: LiveData<String>
    get() = _username
    @Bindable
    var _password = MutableLiveData<String>()
    val password: LiveData<String>
    get() = _password

    @Bindable
    var _repeatPassword = MutableLiveData<String>()
    val repeatPassword: LiveData<String>
    get() = _repeatPassword

    var _token = MutableLiveData<String>()
    val token: LiveData<String>
    get() = _token

    private val _response =  MutableLiveData<String>()
    val response: LiveData<String>
    get() = _response


    init {
        _username.value = ""
        _password.value = ""
        _repeatPassword.value = ""
        _token.value = ""
    }

    fun checkUsername() {
        val user = User(username.value.toString(), password.value.toString())
        BookApi.retrofitService.checkUsername(user).enqueue(object: Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                _token.value = "Failure " + t.message
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                registrate()
            }

        })
    }

    fun registrate() {
        val user = User(username.value.toString(), password.value.toString())
        if(username.value.equals(null) || username.value.equals("") ||password.value.equals(null) || password.value.equals("") || repeatPassword.value.equals(null) || repeatPassword.value.equals("")){
            _token.value = "Fill in all the fields."
            Log.d(_token.value.toString(), "token1")
        }
        else if (password.value.toString() != repeatPassword.value.toString()) {
            _token.value = "Repeat password isn't the same as password."
            Log.d(_token.value.toString(), "token2")
        }
        else {
            BookApi.retrofitService.register(user).enqueue(object : Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    _response.value = "Failure" + t.message
                    _token.value = "Failure" + t.message
                }

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    onUsernameRegister(response, user)
                    _token.value = "true"
                }

            })
        }
    }
    fun onUsernameRegister(response: Response<User>, user:User) {
        Log.d(response.body().toString(), "body voor registratiescherm.")
    }

}