package com.adden00.testtaskunisafe.features.shop_list_screen.presentation.mvi

sealed class ShopListEvent {
    class CreateShopList(val name: String): ShopListEvent()
    object GetAllShopLists: ShopListEvent()
    object LogOut: ShopListEvent()
    class CopyShopListId(val copy: (String)->Unit): ShopListEvent()
}