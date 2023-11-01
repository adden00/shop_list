package ru.usafe.shopping.features.auth_screen.data

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.usafe.shopping.features.auth_screen.data.models.AuthResponse
import ru.usafe.shopping.features.auth_screen.data.models.RegisterResponse


interface AuthApiClient {

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