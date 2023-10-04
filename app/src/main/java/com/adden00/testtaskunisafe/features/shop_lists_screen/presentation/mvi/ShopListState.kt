package com.adden00.testtaskunisafe.features.shop_lists_screen.presentation.mvi

import com.adden00.testtaskunisafe.features.shop_lists_screen.presentation.models.ShopListModel

data class ShopListState(
    val list: List<ShopListModel> = listOf(),
    val isLoading: Boolean = false,
    val isUpdating: Boolean = false
)