package com.example.geoffrey.bibliotheekapp.fragments

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.geoffrey.bibliotheekapp.R
import com.example.geoffrey.bibliotheekapp.activities.LoginActivity
import com.example.geoffrey.bibliotheekapp.activities.MainActivity

open class MenuBaseFragment:Fragment() {
    /**
     * * Called to have the fragent instantiate its user interface view and is called between onCreate(Bundle) and onActivityCreated(Bundle)
     * @property inflater Is sed to inflate any view in the fragment
     * @property container if non-null, this is the parent view that the fragment's UI should be attached to.
     * @property savedInstanceState Bundle: if non-null, this fragment is being re-constructed from a previous saved state as given here
     *
     * @return View? return the View for the fragment's UI, or null
     *
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }
    /**
     * @property menu Defines a Menu, which is a container for menu items
     * @property inflater Returns a MenuInflater with this context.
     *
     * creates the navigator_menu
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.navigator_menu, menu)
    }

    /**
     * When the item is logoutMenu, the person will logout and will be redirected to the logoutAcitivty
     * otherwise the Navigation will navigate to the correct fragment
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId) {
            R.id.logoutMenu -> {
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
                (activity as MainActivity).finish()
            }
        }
        return NavigationUI.onNavDestinationSelected(item!!, view!!.findNavController()) || super.onOptionsItemSelected(item)
    }
}