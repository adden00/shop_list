package com.adden00.testtaskunisafe.features.auth_screen.data

import com.adden00.testtaskunisafe.features.auth_screen.domain.AuthRepository

class AuthRepositoryImpl(private val api: AuthApiClient): AuthRepository {

    override suspend fun createKey(): String {
        return api.createTestKey()
    }

    override suspend fun auth(token: String): Boolean {
        val response = api.auth(token)
        return response.isSuccess
    }


}