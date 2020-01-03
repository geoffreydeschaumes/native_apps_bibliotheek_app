package com.example.geoffrey.bibliotheekapp.utils

import android.app.Activity
import android.content.Context

class LandscapePreference(context:Context) {
    private var CURRENT_LANDSCAPE = "portrait"
    /**
     *  returns the sharedPreference with name myPrefs (token) in private Mode
     */
    private var pref = context.getSharedPreferences(CURRENT_LANDSCAPE, Activity.MODE_PRIVATE)

    /**
     * returns the token saved in the shared preference
     */
    fun getLandscapeOrientation():Boolean{
        return pref.getBoolean(CURRENT_LANDSCAPE, false)
    }

    /**
     * sets the shared preference with the token given when logged in.
     */
    fun setLandscapeOrientation(token:Boolean){
        var editor = pref!!.edit()
        editor.putBoolean(CURRENT_LANDSCAPE, token)
        editor.apply()
    }
}