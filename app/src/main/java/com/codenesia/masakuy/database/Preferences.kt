package com.codenesia.masakuy.database

import android.content.Context

internal class Preferences(context: Context) {
    companion object {
        private const val PREFS_NAME = "preference"

        private const val FIRST_LAUNCH = "first_launch"
        private const val THEME_DARK = "theme_dark"
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setFirstLaunch(value: Boolean) {
        val editor = preferences.edit()
        editor.putBoolean(FIRST_LAUNCH, value)
        editor.apply()
    }

    fun isFirstLaunch(): Boolean {
        val value = preferences.getBoolean(FIRST_LAUNCH, true)
        return value
    }

    fun setThemeDark(value: Boolean) {
        val editor = preferences.edit()
        editor.putBoolean(THEME_DARK, value)
        editor.apply()
    }

    fun isThemeDark(): Boolean {
        val value = preferences.getBoolean(THEME_DARK, false)
        return value
    }
}