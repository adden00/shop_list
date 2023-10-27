package ru.usafe.shopping.features.shop_list_items_screen.presentation.mvi


sealed class ShopListItemsEffect {
    object ShowInternetError: ShopListItemsEffect()
    object Waiting: ShopListItemsEffect()
}