package ru.usafe.shopping.features.auth.presentation.mvi


data class AuthScreenState(
    val isLoading: Boolean = false,
    val recentTokens: List<String> = listOf()
)