package com.adden00.shopping_list.features.shop_lists_screen.data.models.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoveListResponse(
    @SerialName("success")
    val isSuccess: Boolean
)