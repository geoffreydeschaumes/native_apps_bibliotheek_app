package com.example.geoffrey.bibliotheekapp.utils

import android.app.Activity
import android.content.Context


class UserSharedPreferences(var context: Context) {
    private var PREF_NAME = "myPrefs"
    private var CUR_USER = "currentUser"
    /**
     *  returns the sharedPreference with name myPrefs (token) in private Mode
     */
    private var pref = context.getSharedPreferences(CUR_USER,Activity.MODE_PRIVATE)

    /**
     * returns the token saved in the shared preference
     */
    fun getCurrentUser():String{
        return pref.getString(CUR_USER, "")
    }

    /**
     * sets the shared preference with the token given when logged in.
     */
    fun setCurrentUser(token:String){
        var editor = pref!!.edit()
        editor.putString(CUR_USER, token)
        editor.apply()
    }

    /**
     * clears the preference when used (on logout)
     */
    fun clearPreference() {
        var editor = pref.edit()
        editor.clear()
        editor.commit()
    }
}