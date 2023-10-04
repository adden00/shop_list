package com.adden00.testtaskunisafe.features.auth_screen.data

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

}