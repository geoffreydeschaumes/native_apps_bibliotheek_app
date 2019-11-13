package com.example.geoffrey.bibliotheekapp.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.content.Intent
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import androidx.navigation.Navigation
import com.example.geoffrey.bibliotheekapp.activities.MainActivity
import com.example.geoffrey.bibliotheekapp.models.User
import com.example.geoffrey.bibliotheekapp.network.BookApi
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel():  ViewModel() {
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


    init {
        _username.value = ""
        _password.value = ""
        _repeatPassword.value = ""
        _token.value = ""
    }

    fun checkUsername(view:View) {
        val user = User(username.value.toString(), password.value.toString())
        BookApi.retrofitService.checkUsername(user).enqueue(object: Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                _token.value = "Failure " + t.message
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.body()!!.string().length != 25){
                    registrate(view)
                }
                else {
                    _token.value = "The username does exist already!"
                }
            }

        })
    }

    fun registrate(view:View) {
        val user = User(username.value.toString(), password.value.toString())
        if(username.value.equals(null) || username.value.equals("") ||password.value.equals(null) || password.value.equals("") || repeatPassword.value.equals(null) || repeatPassword.value.equals("")){
            _token.value = "Fill in all the fields."
        }
        else if (password.value.toString() != repeatPassword.value.toString()) {
            _token.value = "Repeat password isn't the same as password."
        }
        else {
            BookApi.retrofitService.register(user).enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    _token.value = "Failure" + t.message
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    onUsernameRegister(response, user, view)
                    _token.value = "true"
                }

            })
        }
    }

    fun onUsernameRegister(response: Response<ResponseBody>, user:User, view:View) {
        Log.d(response.body().toString(), "body voor registratiescherm.")
        val i = Intent(view.context, MainActivity::class.java)
        startActivity(view.context, i, null)
    }

}