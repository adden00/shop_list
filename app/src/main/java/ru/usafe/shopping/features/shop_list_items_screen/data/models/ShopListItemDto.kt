package ru.usafe.shopping.features.shop_list_items_screen.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ShopListItemDto(

    @SerialName("created")
    val created: String,

    @SerialName("name")
    val name: String,

    @SerialName("id")
    val id: Int,

    @SerialName ("is_crossed")
    val isCrossed: Boolean
)