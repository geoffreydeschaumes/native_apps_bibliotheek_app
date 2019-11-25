package com.example.geoffrey.bibliotheekapp.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Intent
import android.databinding.Bindable
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import androidx.navigation.findNavController
import com.example.geoffrey.bibliotheekapp.R
import com.example.geoffrey.bibliotheekapp.activities.MainActivity
import com.example.geoffrey.bibliotheekapp.models.User
import com.example.geoffrey.bibliotheekapp.network.BookApi
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel: ViewModel() {
    @Bindable
    var _username = MutableLiveData<String>()
    val username: LiveData<String>
        get() = _username
    @Bindable
    var _password = MutableLiveData<String>()
    val password: LiveData<String>
        get() = _password

    @Bindable
    var _token = MutableLiveData<String>()
    val token: LiveData<String>
        get() = _token

    init {
        _username.value = ""
        _password.value = ""
        _token.value = ""
    }

     fun loginUser(view:View) {
        val user = User(username.value.toString(), password.value.toString())
        BookApi.retrofitService.login(user).enqueue(object: Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                _token.value = "Failure" + t.message
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(_password.value.equals("") || _username.value.equals("")) {
                    _token.value = "Fill in all the fields!"
                }
                else {
                    if(response.code() != 200){
                        _token.value = "User does not excist"
                    }
                    else {
                        onLoginSuccess(response, user, view)
                    }
                }
            }

        })
    }

    fun onLoginSuccess(response: Response<ResponseBody>, user:User, view: View) {
        /*Log.d(response.body().toString(), "body voor registratiescherm.")
        val i = Intent(view.context, MainActivity::class.java)
        ContextCompat.startActivity(view.context, i, null)*/
        view.findNavController().navigate(R.id.action_loginFragment_to_bookListFragment)
    }
    fun registrationPage(view: View){
        view.findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
    }

}