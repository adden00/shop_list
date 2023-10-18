package com.adden00.testtaskunisafe.features.auth_screen.domain.use_cases

import android.content.SharedPreferences
import com.adden00.testtaskunisafe.core.Constants
import com.adden00.testtaskunisafe.features.auth_screen.domain.AuthRepository
import com.adden00.testtaskunisafe.features.auth_screen.domain.models.UserRegisterData
import javax.inject.Inject

class RegisterNewUserUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val prefs: SharedPreferences
) {
    suspend operator fun invoke(data: UserRegisterData) {
        val newToken = repository.register(
            data.name,
            data.email,
            data.phone
        )
        prefs.edit().putString(Constants.TOKEN_KEY, newToken).apply()
    }
}