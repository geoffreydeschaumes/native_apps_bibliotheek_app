package com.example.geoffrey.bibliotheekapp.activities
import android.content.res.Configuration
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.geoffrey.bibliotheekapp.R
import com.example.geoffrey.bibliotheekapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        setLandscapeOrientationPreference()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        setLandscapeOrientationPreference()
    }
    private fun setLandscapeOrientationPreference(){
        landscapePrefs.setLandscapeOrientation(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
    }
}
