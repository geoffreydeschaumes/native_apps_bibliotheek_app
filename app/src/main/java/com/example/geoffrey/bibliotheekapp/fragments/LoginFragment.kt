package com.example.geoffrey.bibliotheekapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.geoffrey.bibliotheekapp.R
import com.example.geoffrey.bibliotheekapp.databinding.FragmentLoginBinding
import com.example.geoffrey.bibliotheekapp.viewModel.LoginViewModel


class LoginFragment : Fragment() {
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate<FragmentLoginBinding>(inflater, R.layout.fragment_login, container, false)
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        binding.loginViewModel = loginViewModel
        binding.setLifecycleOwner(this)
        return binding.root
    }
}
