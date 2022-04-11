package com.codenesia.masakuy

import androidx.appcompat.app.AppCompatDelegate

object Utils {

    fun checkTheme(isThemeDark:Boolean) {
        when (isThemeDark) {
            false -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            else -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }
}