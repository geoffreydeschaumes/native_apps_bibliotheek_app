package com.example.geoffrey.bibliotheekapp.activities

import android.app.SearchManager
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import com.example.geoffrey.bibliotheekapp.R
import com.example.geoffrey.bibliotheekapp.databinding.ActivityMainBinding

/**
 * The MainActivity is the first screen that is opened when the app starts
 * @return AppCompatActivity is a base class for activities that use the support library action bar feature
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    /**
     * binds to the activity_main which is a container for the fragment views
     * calls to setLandscapeOrientationPreference()
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setLandscapeOrientationPreference()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        setLandscapeOrientationPreference()
    }

    /**
     * set the landscapePreference to true or false depending on the orientation of the android device
     */
    private fun setLandscapeOrientationPreference(){
        landscapePrefs.setLandscapeOrientation(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
    }
}
