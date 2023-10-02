package com.adden00.testtaskunisafe.features.start_screen.presentation.mvi

sealed class AuthScreenEffect {
    object Waiting: AuthScreenEffect()
    object NavigateToShopLists: AuthScreenEffect()
    object InternetError: AuthScreenEffect()
    object WrongTokenError: AuthScreenEffect()
}