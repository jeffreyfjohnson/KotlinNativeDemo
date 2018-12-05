package com.gospotcheck.android.kotlinenativedemo

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.gospotcheck.android.mpp.Storage


class PlatformStorage(context: Context) : Storage {
    companion object {
        private const val PREFS_NAME = "count_preferences"
    }

    private val prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)

    override fun retrieveNum(key: String) = prefs.getLong(key, 0)

    override fun saveNum(key: String, num: Long) = prefs.edit().putLong(key, num).apply()
}