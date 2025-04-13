package com.example.broadcastlistener

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object FirstUseTracker {
    private const val PREF_NAME = "first_use_prefs"
    private const val KEY_FIRST_USE_SENT = "first_use_sent"

    fun isFirstUse(context: Context): Boolean {
        val prefs = getPrefs(context)
        return !prefs.getBoolean(KEY_FIRST_USE_SENT, false)
    }

    fun markFirstUseSent(context: Context) {
        getPrefs(context).edit() { putBoolean(KEY_FIRST_USE_SENT, true) }
    }

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }
}
