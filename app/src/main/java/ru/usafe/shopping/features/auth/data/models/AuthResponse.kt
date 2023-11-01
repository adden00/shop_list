package ru.usafe.shopping.features.auth.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    @SerialName("success")
    val isSuccess: Boolean
)
