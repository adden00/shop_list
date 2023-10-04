package com.adden00.testtaskunisafe.features.shop_list_items_screen.presentation.mvi

sealed class ShopListItemsEvent {
    class LoadAllItems(val listId: Int): ShopListItemsEvent()
    class AddNewItems(val listId: Int, val name: String): ShopListItemsEvent()
}
