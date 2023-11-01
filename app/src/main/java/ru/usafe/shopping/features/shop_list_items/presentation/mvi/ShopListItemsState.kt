package ru.usafe.shopping.features.shop_list_items.presentation.mvi

import ru.usafe.shopping.features.shop_list_items.presentation.models.ShopListItemModel

data class ShopListItemsState(
    val list: List<ShopListItemModel> = listOf(),
    val isLoading: Boolean = false,
    val isUpdating: Boolean = false
)