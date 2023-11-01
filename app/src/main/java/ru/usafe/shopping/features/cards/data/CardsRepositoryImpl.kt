package ru.usafe.shopping.features.cards.data

import ru.usafe.shopping.core.FalseSuccessResponseException
import ru.usafe.shopping.features.cards.domain.CardsRepository
import ru.usafe.shopping.features.cards.domain.models.CardModel
import javax.inject.Inject

class CardsRepositoryImpl @Inject constructor(private val api: CardsApiClient) : CardsRepository {
    override suspend fun getCards(token: String): List<CardModel> {
        val response = api.getAllCards(token)
        if (response.isSuccess) {
            return response.cardsList.map { it.toDomain() }
        } else {
            throw FalseSuccessResponseException("get cards response is false")
        }
    }

    override suspend fun addCard(
        cardName: String,
        cardCode: String,
        cardQr: String,
        token: String,
        cardBarcode: String,
        cardHex: String
    ) {
        val response = api.addCard(cardName, cardCode, cardQr, token, cardBarcode, cardHex)
        if (response.isSuccess) {
            return
        } else {
            throw FalseSuccessResponseException("add card response is false")
        }
    }

    override suspend fun updateCard(
        cardName: String,
        cardId: Int,
        cardCode: String,
        cardQr: String,
        token: String,
        cardBarcode: String,
        cardHex: String
    ) {
        val response =
            api.updateCard(cardName, cardId, cardCode, cardQr, token, cardBarcode, cardHex)
        if (response.isSuccess) {
            return
        } else {
            throw FalseSuccessResponseException("add card response is false")
        }
    }

    override suspend fun removeCard(token: String, id: Int) {
        val response = api.removeCard(token, id)
        if (response.isSuccess) {
            return
        } else {
            throw FalseSuccessResponseException("remove cards response is false")
        }
    }
}