package ru.usafe.shopping.features.cards.presentation.models

data class CardModelPres(
    val id: Int,
    val cardName: String,
    val cardCode: String,
    val cardQr: String,
    val cardBarcode: String,
    val cardHex: String,
    val isAdding: Boolean = false
)
