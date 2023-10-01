package com.adden00.testtaskunisafe.features.shop_list_screen.presentation.mvi

import com.adden00.testtaskunisafe.features.shop_list_screen.presentation.models.ShopListModel

data class ShopListState(
    val list: List<ShopListModel> = listOf(),
    val isLoading: Boolean = false
)
