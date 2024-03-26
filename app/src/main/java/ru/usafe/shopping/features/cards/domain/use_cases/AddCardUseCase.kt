package ru.usafe.shopping.features.cards.domain.use_cases

import ru.usafe.shopping.core.AppSettings
import ru.usafe.shopping.core.TokenIsNullException
import ru.usafe.shopping.features.cards.domain.CardsRepository
import ru.usafe.shopping.features.cards.domain.models.CardModel
import javax.inject.Inject

class AddCardUseCase @Inject constructor(
    private val repository: CardsRepository,
    private val appSettings: AppSettings
) {
    suspend operator fun invoke(
        cardName: String,
        cardCode: String,
        cardQr: String,
        cardBarcode: String,
        cardHex: String
    ): List<CardModel> {
        val token = appSettings.activeToken
        if (token == null) {
            throw TokenIsNullException("token is null!")
        } else {
            repository.addCard(cardName, cardCode, cardQr, token, cardBarcode, cardHex)
            return repository.getCards(token)
        }
    }
}