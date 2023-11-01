package ru.usafe.shopping.features.auth.domain.models

data class UserRegisterData(
    val name: String,
    val email: String,
    val phone: String
)
