package com.example.geoffrey.bibliotheekapp.fragments

import android.content.Intent
import androidx.lifecycle.LifecycleOwner
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProviders
import com.example.geoffrey.bibliotheekapp.R
import com.example.geoffrey.bibliotheekapp.activities.MainActivity
import com.example.geoffrey.bibliotheekapp.viewModel.RegisterViewModel
import com.example.geoffrey.bibliotheekapp.databinding.FragmentRegistrationBinding
import kotlinx.android.synthetic.main.fragment_registration.*


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btnRegistration.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            registerViewModel.checkUsername(it, startActivity(intent))
        }
    }


}
