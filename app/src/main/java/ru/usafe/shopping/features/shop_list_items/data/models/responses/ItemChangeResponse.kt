package ru.usafe.shopping.features.shop_list_items.data.models.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ItemChangeResponse(
    @SerialName ("success")
    val isSuccess: Boolean,
)