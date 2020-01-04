package com.example.geoffrey.bibliotheekapp.activities

import android.app.Application
import com.example.geoffrey.bibliotheekapp.utils.LandscapePreference
import com.example.geoffrey.bibliotheekapp.utils.UserSharedPreferences

/**
 * App.prefs!! is called from a companion object named App
 * This variable is created by lazy which means that it is created by first access
 */
val prefs:UserSharedPreferences by lazy {
    App.prefs!!
}
/**
 * App.landscapePreference!! is called from a companion object named App
 * This variable is created by lazy which means that it is created by first access
 */
val landscapePrefs:LandscapePreference by lazy {
    App.landscapePreference!!
}

/**
 * Class App creates objects that are used over multiple activities
 *
 * @return Application()
 */
class App:Application() {
    /**
     * Companion object create variables prefs and landscapePreference initialized by null
     * A companion object is a singleton and the members can be accessed directly via the name of the containing class
     */
    companion object{
        var prefs: UserSharedPreferences? = null
        var landscapePreference:LandscapePreference? = null
    }

    /**
     * OnCreate(), initializes the variable prefs and landscapePreference
     *
     */
    override fun onCreate() {
        prefs = UserSharedPreferences(applicationContext)
        landscapePreference = LandscapePreference(applicationContext)
        super.onCreate()
    }
}