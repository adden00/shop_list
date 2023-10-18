package com.adden00.shopping_list.features.shop_lists_screen.presentation.mvi

sealed class ShopListEvent {
    class CreateShopList(val name: String): ShopListEvent()
    object GetAllShopLists: ShopListEvent()
    object LogOut: ShopListEvent()
    object GetAccountId: ShopListEvent()
    class CopyShopListId(val copy: (String)->Unit): ShopListEvent()
    class RemoveShopList(val listId: Int): ShopListEvent()
}