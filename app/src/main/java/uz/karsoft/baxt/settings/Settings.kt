package uz.karsoft.baxt.settings

import android.content.Context
import android.content.SharedPreferences

class Settings(context: Context) {
    companion object {
        const val TOKEN = "token"
        const val LOGGED_IN = "logged_in"
        const val LANGUAGE = "language"
        const val NAME = "name"
    }
    private val preferences: SharedPreferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

    /** for saving referer */
    var token: String
        set(value) = preferences.edit().putString(TOKEN, value).apply()
        get() = preferences.getString(TOKEN, "") ?: ""

    /** check user status */
    var loggedIn: Boolean
        set(value) = preferences.edit().putBoolean(LOGGED_IN, value).apply()
        get() = preferences.getBoolean(LOGGED_IN, false)


    /** app language*/
    var language: String
        set(value) = preferences.edit().putString(LANGUAGE, value).apply()
        get() = preferences.getString(LANGUAGE, "") ?: ""

    /** user name*/
    var name: String
        set(value) = preferences.edit().putString(NAME, value).apply()
        get() = preferences.getString(NAME, "") ?: ""
}