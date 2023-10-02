package com.adden00.testtaskunisafe.features.shop_lists_screen.data.models.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateShopListsResponse(
    @SerialName("success")
    val isSuccess: Boolean,

    @SerialName("list_id")
    val id: Int
)
