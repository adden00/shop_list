package com.adden00.testtaskunisafe.features.auth_screen.presentation.mvi

sealed class AuthScreenEffect {
    object Waiting: AuthScreenEffect()
    object NavigateToShopLists: AuthScreenEffect()
    object InternetError: AuthScreenEffect()
    object WrongTokenError: AuthScreenEffect()
}