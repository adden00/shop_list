package ru.usafe.shopping.core

import android.content.SharedPreferences
import javax.inject.Inject

class AppSettings @Inject constructor(private val prefs: SharedPreferences) {

    var activeToken: String?
        get() = prefs.getString(ACTIVE_TOKEN, null)
        set(value) {
            prefs.edit().putString(ACTIVE_TOKEN, value).apply()
        }

    var allTokens: List<String>
        get() = prefs.getStringSet(ALL_TOKENS, setOf())?.toList() ?: listOf()
        set(value) {
            prefs.edit().putStringSet(ALL_TOKENS, value.toHashSet()).apply()
        }

    fun addToken(token: String) {
        allTokens = allTokens.toMutableList().apply { add(token) }.toList()
    }

    fun removeToken(token: String) {
        val curTokens = allTokens.toMutableList()
        curTokens.remove(token)
        allTokens = curTokens.toList()
    }


    companion object {
        private const val ACTIVE_TOKEN = "ACTIVE_TOKEN"
        private const val ALL_TOKENS = "ALL_TOKENS"
    }
}