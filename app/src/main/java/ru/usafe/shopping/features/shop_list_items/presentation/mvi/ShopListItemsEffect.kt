package ru.usafe.shopping.features.shop_list_items.presentation.mvi


sealed class ShopListItemsEffect {
    object ShowInternetError: ShopListItemsEffect()
    object Waiting: ShopListItemsEffect()
}