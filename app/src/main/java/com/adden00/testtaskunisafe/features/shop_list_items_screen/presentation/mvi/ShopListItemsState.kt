package com.adden00.testtaskunisafe.features.shop_list_items_screen.presentation.mvi

import com.adden00.testtaskunisafe.features.shop_list_items_screen.presentation.models.ShopListItemModel

data class ShopListItemsState(
    val list: List<ShopListItemModel> = listOf(),
    val isLoading: Boolean = false,
    val isUpdating: Boolean = false
)