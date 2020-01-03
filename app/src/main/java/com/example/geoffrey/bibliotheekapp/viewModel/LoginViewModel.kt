package com.example.geoffrey.bibliotheekapp.viewModel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.example.geoffrey.bibliotheekapp.R
import com.example.geoffrey.bibliotheekapp.activities.LoginActivity
import com.example.geoffrey.bibliotheekapp.activities.prefs
import com.example.geoffrey.bibliotheekapp.models.User
import com.example.geoffrey.bibliotheekapp.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val userRepo = UserRepository()
    var _username = MutableLiveData<String>()
    val username: LiveData<String>
        get() = _username


    var _password = MutableLiveData<String>()
    val password: LiveData<String>
        get() = _password


    var _token = MutableLiveData<String>()
    val token: LiveData<String>
        get() = _token

    init {
        _username.value = ""
        _password.value = ""
        _token.value = ""
    }
     fun loginUser(view:View, intent:Intent) {
         val user = User(username.value.toString(), password.value.toString())
         coroutineScope.launch {
             try {
                 userRepo.login(user)
                 prefs.setCurrentUser(userRepo.login(user).string())
                 if(user.username == "administrator" && user.password == "administrator") {
                     onLoginSuccessAdministrator(view)
                 }
                 else {
                     intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                     view.context.applicationContext.startActivity(intent)
                 }
             }
             catch (e: Exception) {
                 _token.value = e.message
             }
         }
    }
    private fun onLoginSuccessAdministrator(view:View) {
        view.findNavController().navigate(R.id.action_loginFragment_to_administratorOverviewFragment)
    }
    fun registrationPage(view: View){
        view.findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
    }

}