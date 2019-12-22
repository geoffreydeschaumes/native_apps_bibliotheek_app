package com.example.geoffrey.bibliotheekapp.viewModel

import android.util.Log
import androidx.databinding.Bindable
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.example.geoffrey.bibliotheekapp.R
import com.example.geoffrey.bibliotheekapp.models.User
import com.example.geoffrey.bibliotheekapp.network.BookApi
import com.example.geoffrey.bibliotheekapp.repositories.UserRepository
import kotlinx.coroutines.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class RegisterViewModel:  ViewModel() {
    private val userRepo = UserRepository()

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

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    init {
        _username.value = ""
        _password.value = ""
        _repeatPassword.value = ""
        _token.value = ""
    }

    fun checkUsername(view:View) {
        val user = User(username.value.toString(), password.value.toString())
        if(password != repeatPassword) {
            _token.value = "password and repeat password aren't similar!"
        } else {
            registrate(user, view)
        }
    }
    private fun registrate(user:User, view:View){
        coroutineScope.launch {
            try {
                val checkuser = userRepo.checkUsername(user)
                if (checkuser.contentLength().toString() == "25") {
                    _token.value = "User does already exist!"
                } else {
                    userRepo.registrate(user)
                    onUsernameRegister(view)
                }
            } catch (e: Exception) {
                _token.value = e.message

            }
        }
    }


    fun onUsernameRegister(view:View) {
        view.findNavController().navigate(R.id.action_registrationFragment_to_bookListFragment)
    }

}