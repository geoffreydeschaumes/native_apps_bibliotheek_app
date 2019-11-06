package com.example.geoffrey.bibliotheekapp.fragments


import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

import com.example.geoffrey.bibliotheekapp.R
import com.example.geoffrey.bibliotheekapp.viewModel.LoginViewModel
import com.example.geoffrey.bibliotheekapp.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding:FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
       
        binding = DataBindingUtil.inflate<FragmentLoginBinding>(inflater, R.layout.fragment_login, container, false)

        binding.txtSignUp.setOnClickListener {
            view:View -> Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registrationFragment)
        }
        return binding.root
    }
}
