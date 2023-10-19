package com.adden00.shopping_list.features.cards.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateCardsResponse(
    @SerialName("success")
    val isSuccess: Boolean
)
