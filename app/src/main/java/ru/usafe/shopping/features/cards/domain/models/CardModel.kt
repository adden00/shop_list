package ru.usafe.shopping.features.cards.domain.models

data class CardModel(
    val id: Int,
    val cardName: String,
    val cardCode: String,
    val cardQr: String,
    val cardBarcode: String,
    val cardHex: String
)