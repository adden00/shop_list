package ru.usafe.shopping.features.shop_list_items.presentation.models

data class ShopListItemModel(
    val created: String,
    val id: Int,
    val name: String,
    val isCrossed: Boolean
)