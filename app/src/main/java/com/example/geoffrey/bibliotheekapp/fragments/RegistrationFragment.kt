package com.example.geoffrey.bibliotheekapp.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.geoffrey.bibliotheekapp.R
import com.example.geoffrey.bibliotheekapp.viewModel.RegisterViewModel
import com.example.geoffrey.bibliotheekapp.databinding.FragmentRegistrationBinding


class RegistrationFragment : Fragment() {

    private lateinit var registerViewModel: RegisterViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentRegistrationBinding>(inflater, R.layout.fragment_registration, container, false)
        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)
        binding.registerViewModel = registerViewModel
        binding.setLifecycleOwner(this)

                

        return binding.root
    }


}
