package com.adden00.shopping_list.features.auth_screen.domain.use_cases

import android.content.SharedPreferences
import com.adden00.shopping_list.core.Constants
import com.adden00.shopping_list.features.auth_screen.domain.AuthRepository
import javax.inject.Inject

class CreateTokenUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val prefs: SharedPreferences
) {
    suspend operator fun invoke() {
        val newToken = repository.createKey()
        prefs.edit().putString(Constants.TOKEN_KEY, newToken).apply()
    }
}