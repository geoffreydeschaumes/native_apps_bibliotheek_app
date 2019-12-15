package com.example.geoffrey.bibliotheekapp.viewModel

import androidx.databinding.Bindable
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.example.geoffrey.bibliotheekapp.R
import com.example.geoffrey.bibliotheekapp.models.User
import com.example.geoffrey.bibliotheekapp.network.BookApi
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel:  ViewModel() {


    var _username = MutableLiveData<String>()
    val username: LiveData<String>
        get() = _username

    var _password = MutableLiveData<String>()
    val password: LiveData<String>
        get() = _password


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
        /*Log.d(response.body().toString(), "body voor registratiescherm.")
        val i = Intent(view.context, MainActivity::class.java)
        startActivity(view.context, i, null)
        */
        view.findNavController().navigate(R.id.action_registrationFragment_to_bookListFragment)
    }

}