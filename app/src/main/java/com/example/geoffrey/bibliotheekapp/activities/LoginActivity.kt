package com.example.geoffrey.bibliotheekapp.activities
import android.content.res.Configuration
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.geoffrey.bibliotheekapp.R
import com.example.geoffrey.bibliotheekapp.databinding.ActivityLoginBinding

/**
 * The LoginActivity is the first screen that is opened when the app starts
 * @return AppCompatActivity is a base class for activities that use the support library action bar feature
 */
class LoginActivity : AppCompatActivity() {
    /**
     * Makes binding possible to get the username and password from the loginView
     * @return ActivityLoginBinding is automatically created from as binding for the fragment_login view
     */
    private lateinit var binding:ActivityLoginBinding

    /**
     * binds to the activity_login which is a container for the fragment views
     * calls to setLandscapeOrientationPreference()
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
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
