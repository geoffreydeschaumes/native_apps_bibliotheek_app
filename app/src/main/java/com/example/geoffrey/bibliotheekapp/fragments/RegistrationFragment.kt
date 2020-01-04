package com.example.geoffrey.bibliotheekapp.fragments

import android.content.Context
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


class RegistrationFragment : Fragment() {

    private lateinit var registerViewModel: RegisterViewModel
    /**
     * Called to have the fragent instantiate its user interface view and is called between onCreate(Bundle) and onActivityCreated(Bundle)
     * @property inflater Is sed to inflate any view in the fragment
     * @property container if non-null, this is the parent view that the fragment's UI should be attached to.
     * @property savedInstanceState Bundle: if non-null, this fragment is being re-constructed from a previous saved state as given here
     *
     * @return View? return the View for the fragment's UI, or null
     *
     */
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

    /**
     * @property view The view returned by onCreateView(LayoutInflater, ViewGroup, Bundle)
     * @property savedInstanceState Bundle: If non-null, this fragment is being re-constructed from a previous saved state as given here. This value may be null.
     * When clicked on btnRegistration in the registration_fragment the checkUsername(it, intent) is called from registerViewModel
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btnRegistration.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            registerViewModel.checkUsername(it, intent)
        }
    }


}
