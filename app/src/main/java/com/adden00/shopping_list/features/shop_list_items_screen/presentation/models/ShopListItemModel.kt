package com.adden00.shopping_list.features.shop_list_items_screen.presentation.models

data class ShopListItemModel(
    val created: String,
    val id: Int,
    val name: String,
    val isCrossed: Boolean
)