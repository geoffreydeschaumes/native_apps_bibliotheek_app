package com.example.geoffrey.bibliotheekapp.viewModel

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.databinding.Bindable
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.example.geoffrey.bibliotheekapp.R
import com.example.geoffrey.bibliotheekapp.activities.prefs
import com.example.geoffrey.bibliotheekapp.models.User
import com.example.geoffrey.bibliotheekapp.network.BookApi
import com.example.geoffrey.bibliotheekapp.repositories.UserRepository
import kotlinx.coroutines.*
import okhttp3.ResponseBody
import okhttp3.internal.lockAndWaitNanos
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.util.*

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

    fun checkUsername(view:View, intent:Intent) {
        val user = User(username.value.toString(), password.value.toString())
        if (_password.value != _repeatPassword.value || _username.value == "" || _password.value == "") {
            _token.value = "Fill in all the fields correctly!"
        } else {
            coroutineScope.launch {
                     registrate(user, view, intent)
            }
        }
    }

    private suspend fun registrate(user: User, view:View, intent:Intent) {
                coroutineScope.launch {
                    try {
                        if (userRepo.checkUsername(user).body()?.username != "bestaat al") {
                            val userRegistration = userRepo.registrate(user)
                            if (userRegistration.code() == 200) {
                                prefs.setCurrentUser(userRegistration.body()?.string().toString())
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                view.context.applicationContext.startActivity(intent)
                            }
                        }
                        else {
                            _token.value = "User does already exist!"
                        }
                    } catch (e: Exception) {
                        _token.value = e.message
                    }
                }
    }
}