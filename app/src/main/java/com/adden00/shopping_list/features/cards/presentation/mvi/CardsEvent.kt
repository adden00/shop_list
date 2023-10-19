package com.adden00.shopping_list.features.cards.presentation.mvi

sealed interface CardsEvent {
    object GetCards : CardsEvent
    class AddCard(
        val cardName: String,
        val cardCode: String,
        val cardQr: String,
        val cardBarcode: String,
        val cardHex: String
    ) : CardsEvent

    class RemoveCard(val id: Int) : CardsEvent
}