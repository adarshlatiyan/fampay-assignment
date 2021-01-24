package com.adarsh.dynamicui.util

import android.content.Context

class PrefsHelper(context: Context) {
    companion object {
        private const val PREFS_NAME = "MyPrefs"

        private const val DISMISSED_CARDS = "dismissed_cards"
    }

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun putDismissedCard(name: String) {
        val editor = prefs.edit()
        editor.putString(DISMISSED_CARDS, name)
        editor.apply()
    }

    fun getDismissedCard(): String? {
        return prefs.getString(DISMISSED_CARDS, "")
    }
}