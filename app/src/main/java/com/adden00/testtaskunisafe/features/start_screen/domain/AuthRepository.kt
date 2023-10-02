package com.adden00.testtaskunisafe.features.start_screen.domain

interface AuthRepository {
    suspend fun createKey(): String
    suspend fun auth(token: String): Boolean

}