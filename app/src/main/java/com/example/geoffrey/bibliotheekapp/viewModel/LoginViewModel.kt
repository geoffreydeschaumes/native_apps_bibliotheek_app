package com.example.geoffrey.bibliotheekapp.viewModel

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.example.geoffrey.bibliotheekapp.R
import com.example.geoffrey.bibliotheekapp.models.User
import com.example.geoffrey.bibliotheekapp.repositories.UserRepository

class LoginViewModel: ViewModel() {


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
    private val userRepo = UserRepository()
     fun loginUser(view:View) {
         val user = User(username.value.toString(), password.value.toString())
         val userLogin = userRepo.login(user)
         if(userLogin == "") {
             onLoginSuccess(view)
         } else {
             _token.value = userLogin
         }
    }

    private fun onLoginSuccess(view: View) {
        view.findNavController().navigate(R.id.action_loginFragment_to_bookListFragment)
    }
    fun registrationPage(view: View){
        view.findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
    }

}