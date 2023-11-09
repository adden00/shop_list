package ru.usafe.shopping.features.auth.domain.use_cases

import android.content.SharedPreferences
import ru.usafe.shopping.core.Constants
import ru.usafe.shopping.features.auth.domain.AuthRepository
import ru.usafe.shopping.features.auth.domain.models.UserRegisterData
import javax.inject.Inject

class RegisterNewUserUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val prefs: SharedPreferences
) {
    suspend operator fun invoke(data: UserRegisterData) {
        val newToken = repository.register(
            data.name,
            data.email,
//            data.phone
        )
        prefs.edit().putString(Constants.TOKEN_KEY, newToken).apply()
    }
}