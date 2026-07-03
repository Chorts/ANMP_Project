package com.gukguk.habittracker.util

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)

    fun saveLoginStatus(isLoggedIn: Boolean) {
        prefs.edit().putBoolean("isLoggedIn", isLoggedIn).apply()
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean("isLoggedIn", false)
    }

    fun saveLogin(userId: Int)
    {
        prefs.edit().putBoolean("isLoggedIn", true).apply()
    }

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}