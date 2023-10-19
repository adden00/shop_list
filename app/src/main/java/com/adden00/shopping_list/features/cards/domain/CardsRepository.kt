package com.adden00.shopping_list.features.cards.domain

import com.adden00.shopping_list.features.cards.domain.models.CardModel

interface CardsRepository {
    suspend fun getCards(token: String): List<CardModel>
    suspend fun addCard(
        cardName: String,
        cardCode: String,
        cardQr: String,
        token: String,
        cardBarcode: String,
        cardHex: String
    )

    suspend fun removeCard(token: String, id: Int)
}