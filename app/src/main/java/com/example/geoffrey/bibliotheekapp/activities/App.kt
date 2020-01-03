package com.example.geoffrey.bibliotheekapp.activities

import android.app.Application
import com.example.geoffrey.bibliotheekapp.utils.LandscapePreference
import com.example.geoffrey.bibliotheekapp.utils.UserSharedPreferences

val prefs:UserSharedPreferences by lazy {
    App.prefs!!
}
val landscapePrefs:LandscapePreference by lazy {
    App.landscapePreference!!
}

class App:Application() {
    companion object{
        var prefs: UserSharedPreferences? = null
        var landscapePreference:LandscapePreference? = null
    }

    override fun onCreate() {
        prefs = UserSharedPreferences(applicationContext)
        landscapePreference = LandscapePreference(applicationContext)
        super.onCreate()
    }
}