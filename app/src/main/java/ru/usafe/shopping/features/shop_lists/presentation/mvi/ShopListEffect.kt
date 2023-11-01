package ru.usafe.shopping.features.shop_lists.presentation.mvi

sealed class ShopListEffect {
    data object Waiting : ShopListEffect()
    data object LogOut : ShopListEffect()
    data object InternetError : ShopListEffect()
    data class ShowMessage(val message: String) : ShopListEffect()
}