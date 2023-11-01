package ru.usafe.shopping.features.shop_lists.data.models.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateShopListsResponse(
    @SerialName("success")
    val isSuccess: Boolean,

    @SerialName("list_id")
    val id: Int
)
