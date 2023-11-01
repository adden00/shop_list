package ru.usafe.shopping.features.auth.data

import ru.usafe.shopping.core.CouldNotCreateAccountException
import ru.usafe.shopping.features.auth.domain.AuthRepository

class AuthRepositoryImpl(private val api: AuthApiClient): AuthRepository {

    override suspend fun auth(token: String): Boolean {
        val response = api.auth(token)
        return response.isSuccess
    }

    override suspend fun register(name: String, email: String, phone: String): String {
        val response = api.registerNewUser(
            name, email, phone
        )
        if (response.isSuccess) {
            return response.key
        }
        else
            throw CouldNotCreateAccountException("creation failed")
    }
}