package ru.usafe.shopping.features.cards.domain.use_cases

import android.content.SharedPreferences
import ru.usafe.shopping.core.Constants
import ru.usafe.shopping.core.TokenIsNullException
import ru.usafe.shopping.features.cards.domain.CardsRepository
import ru.usafe.shopping.features.cards.domain.models.CardModel

import javax.inject.Inject

class GetAllCardsUseCase @Inject constructor(
    private val repository: CardsRepository,
    private val prefs: SharedPreferences
) {
    suspend operator fun invoke(): List<CardModel> {
        val token = prefs.getString(Constants.TOKEN_KEY, null)
        if (token == null) {
            throw TokenIsNullException("token is null!")
        } else {
            return repository.getCards(token)
        }
    }
}