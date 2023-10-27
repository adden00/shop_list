package ru.usafe.shopping.features.shop_lists_screen.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShopListResponse(
    @SerialName("shop_list")
    val shopLists: List<ShopListDto>,

    @SerialName("success")
    val isSuccess: Boolean
)

@Serializable
data class ShopListDto(
    @SerialName ("id")
    val id: Int,

    @SerialName ("name")
    val name: String,

    @SerialName ("created")
    val created: String
)
