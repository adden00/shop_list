package ru.usafe.shopping.features.shop_list_items_screen.domain.models

data class ShopListItemModelDomain(
    val created: String,
    val name: String,
    val id: Int,
    val isCrossed: Boolean
)