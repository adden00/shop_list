package ru.usafe.shopping.features.auth.domain.use_cases

import ru.usafe.shopping.core.AppSettings
import ru.usafe.shopping.features.auth.domain.AuthRepository
import javax.inject.Inject

class LogInUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val appSettings: AppSettings
) {
    suspend operator fun invoke(token: String): Boolean {
        val success = repository.auth(token)
        if (success) {
            appSettings.activeToken = token
            appSettings.addToken(token)
        }
        return success
    }
}