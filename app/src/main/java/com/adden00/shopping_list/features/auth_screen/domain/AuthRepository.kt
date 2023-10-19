package com.adden00.shopping_list.features.auth_screen.domain

interface AuthRepository {
    suspend fun auth(token: String): Boolean
    suspend fun register(
        name: String,
        email: String,
        phone: String
    ): String
}