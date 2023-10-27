package ru.usafe.shopping.features.shop_lists_screen.presentation.mvi

import ru.usafe.shopping.features.shop_lists_screen.presentation.models.ShopListModel

data class ShopListState(
    val id: String? = null,
    val list: List<ShopListModel> = listOf(),
    val isLoading: Boolean = false,
    val isUpdating: Boolean = false
)
