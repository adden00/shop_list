package com.adden00.shopping_list.features.cards.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetCardsResponse(
    @SerialName("SUCCESS")
    val isSuccess: Boolean,
    @SerialName("CARD_LIST")
    val cardsList: List<CardDto>
)

@Serializable
data class CardDto(
    @SerialName("ID")
    val id: Int,
    @SerialName("CARD_NAME")
    val cardName: String,
    @SerialName("CARD_CODE")
    val cardCode: String,
    @SerialName("CARD_QR")
    val cardQr: String,
    @SerialName("CARD_BARCODE")
    val cardBarcode: String,
    @SerialName("CARD_HEX")
    val cardHex: String
)