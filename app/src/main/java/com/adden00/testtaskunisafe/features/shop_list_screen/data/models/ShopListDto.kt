package com.adden00.testtaskunisafe.features.shop_list_screen.data.models

data class ShopListDto(
    val id: Int,
    val isSuccess: Boolean,
    val items: List<ShipListItem>
)

data class ShipListItem(
    val id: Int,
    val created: Int,
    val name: String
)
