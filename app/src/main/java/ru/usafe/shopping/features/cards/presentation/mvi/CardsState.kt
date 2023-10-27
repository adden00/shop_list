package ru.usafe.shopping.features.cards.presentation.mvi

import ru.usafe.shopping.features.cards.presentation.models.CardModelPres

data class CardsState(
    val isLoading: Boolean = false,
    val cardsList: List<CardModelPres> = listOf(),
)
