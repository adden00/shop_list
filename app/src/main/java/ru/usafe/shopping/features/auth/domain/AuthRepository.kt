package ru.usafe.shopping.features.auth.domain

interface AuthRepository {
    suspend fun auth(token: String): Boolean
    suspend fun register(
        name: String,
        email: String,
        phone: String
    ): String
}