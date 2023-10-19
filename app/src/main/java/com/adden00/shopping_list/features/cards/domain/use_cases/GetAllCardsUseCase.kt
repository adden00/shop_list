package com.adden00.shopping_list.features.cards.domain.use_cases

import android.content.SharedPreferences
import com.adden00.shopping_list.core.Constants
import com.adden00.shopping_list.core.TokenIsNullException
import com.adden00.shopping_list.features.cards.domain.CardsRepository
import com.adden00.shopping_list.features.cards.domain.models.CardModel

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