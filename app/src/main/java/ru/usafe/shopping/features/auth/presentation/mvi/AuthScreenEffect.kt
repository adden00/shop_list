package ru.usafe.shopping.features.auth.presentation.mvi

sealed class AuthScreenEffect {
    object Waiting: AuthScreenEffect()
    object NavigateToShopLists: AuthScreenEffect()
    object InternetError: AuthScreenEffect()
    object WrongTokenError: AuthScreenEffect()
}