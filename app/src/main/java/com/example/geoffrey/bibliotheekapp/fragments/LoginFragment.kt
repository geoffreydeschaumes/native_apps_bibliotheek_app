package com.example.geoffrey.bibliotheekapp.fragments

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.geoffrey.bibliotheekapp.R
import com.example.geoffrey.bibliotheekapp.activities.LoginActivity
import com.example.geoffrey.bibliotheekapp.activities.MainActivity
import com.example.geoffrey.bibliotheekapp.databinding.FragmentLoginBinding
import com.example.geoffrey.bibliotheekapp.viewModel.LoginViewModel
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment() {
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding
    /**
     *  Called to have the fragent instantiate its user interface view and is called between onCreate(Bundle) and onActivityCreated(Bundle)
     * @property inflater Is sed to inflate any view in the fragment
     * @property container if non-null, this is the parent view that the fragment's UI should be attached to.
     * @property savedInstanceState Bundle: if non-null, this fragment is being re-constructed from a previous saved state as given here
     *
     * @return View? return the View for the fragment's UI, or null
     *
     * creates loginViewModel
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        binding.loginViewModel = loginViewModel
        binding.setLifecycleOwner(this)
        return binding.root
    }
    /**
    * @property view The view returned by onCreateView(LayoutInflater, ViewGroup, Bundle)
    * @property savedInstanceState Bundle: If non-null, this fragment is being re-constructed from a previous saved state as given here. This value may be null.
     * When clicked on btnLogin in the login_fragment login(it) is called
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btnLogin.setOnClickListener {
            login(it)
        }
    }

    /**
     * @property view passes the current view to the method
     * Creates an intent and passes it to loginUser from loginViewModel
     */
    fun login(view:View){
        val intent = Intent(activity, MainActivity::class.java)
        loginViewModel.loginUser(view, intent)

    }
}
