package ru.usafe.shopping.features.cards.presentation.utills

import ru.usafe.shopping.features.cards.presentation.models.CardModelPres

fun List<CardModelPres>.addEmptyCard(): List<CardModelPres> {
    val result = this.toMutableList()
    result.add(
        CardModelPres(
            -1, "", "", "", "", "", isAdding = true
        )
    )
    return result.toList()

}