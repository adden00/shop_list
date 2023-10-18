package com.adden00.shopping_list.features.auth_screen.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    @SerialName("success")
    val isSuccess: Boolean
)
