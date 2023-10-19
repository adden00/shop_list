package com.adden00.shopping_list.features.cards.presentation.utills

import com.adden00.shopping_list.features.cards.presentation.models.CardModelPres

fun List<CardModelPres>.addEmptyCard(): List<CardModelPres> {
    val result = this.toMutableList()
    result.add(
        CardModelPres(
            -1, "", "", "", "", "", isAdding = true
        )
    )
    return result.toList()

}