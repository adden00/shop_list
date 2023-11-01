package ru.usafe.shopping.features.shop_lists.data.models.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoveListResponse(
    @SerialName("success")
    val isSuccess: Boolean
)