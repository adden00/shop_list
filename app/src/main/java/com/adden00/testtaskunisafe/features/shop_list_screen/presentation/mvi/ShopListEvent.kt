package com.adden00.testtaskunisafe.features.shop_list_screen.presentation.mvi

sealed class ShopListEvent {
    object GetToken : ShopListEvent()
}