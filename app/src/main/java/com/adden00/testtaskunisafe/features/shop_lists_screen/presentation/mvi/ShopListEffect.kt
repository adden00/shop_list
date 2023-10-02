package com.adden00.testtaskunisafe.features.shop_lists_screen.presentation.mvi

sealed class ShopListEffect {
    object Waiting: ShopListEffect()
    object LogOut: ShopListEffect()
    object InternetError: ShopListEffect()
    class ShowMessage(val message: String): ShopListEffect()
}