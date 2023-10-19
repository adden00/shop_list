package com.adden00.shopping_list.features.cards.presentation.models

data class CardModelPres(
    val id: Int,
    val cardName: String,
    val cardCode: String,
    val cardQr: String,
    val cardBarcode: String,
    val cardHex: String,
    val isAdding: Boolean = false
)
