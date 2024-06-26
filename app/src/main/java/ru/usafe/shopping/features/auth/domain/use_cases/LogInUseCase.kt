package ru.usafe.shopping.features.auth.domain.use_cases

import android.content.SharedPreferences
import ru.usafe.shopping.core.Constants
import ru.usafe.shopping.features.auth.domain.AuthRepository
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