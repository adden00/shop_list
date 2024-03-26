package ru.usafe.shopping.features.auth.domain.use_cases

import ru.usafe.shopping.core.AppSettings
import ru.usafe.shopping.features.auth.domain.AuthRepository
import ru.usafe.shopping.features.auth.domain.models.UserRegisterData
import javax.inject.Inject

class RegisterNewUserUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val appSettings: AppSettings
) {
    suspend operator fun invoke(data: UserRegisterData) {
        val newToken = repository.register(
            data.name,
            data.email,
//            data.phone
        )
        appSettings.activeToken = newToken
        appSettings.addToken(newToken)
    }
}