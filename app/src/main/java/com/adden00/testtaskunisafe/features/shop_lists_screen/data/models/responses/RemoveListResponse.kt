package com.adden00.testtaskunisafe.features.shop_lists_screen.data.models.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoveListResponse(
    @SerialName("success")
    val isSuccess: Boolean
)