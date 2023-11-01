package ru.usafe.shopping.features.cards.presentation.mvi

sealed interface CardsEvent {
    object GetCards : CardsEvent
    class AddCard(
        val cardName: String,
        val cardCode: String,
        val cardQr: String,
        val cardBarcode: String,
        val cardHex: String
    ) : CardsEvent

    class UpdateCard(
        val cardName: String,
        val cardId: Int,
        val cardCode: String,
        val cardQr: String,
        val cardBarcode: String,
        val cardHex: String
    ) : CardsEvent

    class RemoveCard(val id: Int) : CardsEvent
    object ClearCards : CardsEvent
    class SearchCards(val query: String) : CardsEvent
}