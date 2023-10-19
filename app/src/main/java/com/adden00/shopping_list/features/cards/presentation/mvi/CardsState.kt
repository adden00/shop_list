package com.adden00.shopping_list.features.cards.presentation.mvi

import com.adden00.shopping_list.features.cards.presentation.models.CardModelPres

data class CardsState(
    val isLoading: Boolean = false,
    val cardsList: List<CardModelPres> = listOf(),
)
