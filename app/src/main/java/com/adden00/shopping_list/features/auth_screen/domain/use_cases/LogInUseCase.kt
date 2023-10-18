package com.adden00.shopping_list.features.auth_screen.domain.use_cases

import android.content.SharedPreferences
import com.adden00.shopping_list.core.Constants
import com.adden00.shopping_list.features.auth_screen.domain.AuthRepository
import javax.inject.Inject

class LogInUseCase @Inject constructor(private val repository: AuthRepository, private val prefs: SharedPreferences) {
    suspend operator fun invoke(token: String): Boolean {
        val success = repository.auth(token)
        if (success) {
            prefs.edit().putString(Constants.TOKEN_KEY, token).apply()
        }
        return success
    }
}