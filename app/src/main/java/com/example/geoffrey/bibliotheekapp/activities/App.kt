package com.example.geoffrey.bibliotheekapp.activities

import android.app.Application
import com.example.geoffrey.bibliotheekapp.utils.UserSharedPreferences

val prefs:UserSharedPreferences by lazy {
    App.prefs!!
}

class App:Application() {
    companion object{
        var prefs: UserSharedPreferences? = null
    }

    override fun onCreate() {
        prefs = UserSharedPreferences(applicationContext)
        super.onCreate()
    }
}