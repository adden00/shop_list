package ru.usafe.shopping.features.shop_lists.presentation.mvi

import ru.usafe.shopping.features.shop_lists.presentation.models.ShopListModel

data class ShopListState(
    val id: String? = null,
    val list: List<ShopListModel> = listOf(),
    val isLoading: Boolean = false,
    val isUpdating: Boolean = false
)
