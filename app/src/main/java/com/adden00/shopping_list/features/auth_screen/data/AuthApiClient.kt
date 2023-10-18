package com.adden00.shopping_list.features.auth_screen.data

import com.adden00.shopping_list.features.auth_screen.data.models.AuthResponse
import com.adden00.shopping_list.features.auth_screen.data.models.RegisterResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface AuthApiClient {

    @POST ("CreateTestKey")
    suspend fun createTestKey(): String

    @GET ("Authentication")
    suspend fun auth(
        @Query("key") token: String
    ): AuthResponse

    @POST ("Registration")
    suspend fun registerNewUser (
        @Query("name") name: String,
        @Query("mail") mail: String,
        @Query("tel") phone: String
    ): RegisterResponse
}