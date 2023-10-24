package com.adden00.shopping_list.core.utills

import com.adden00.shopping_list.features.cards.presentation.models.CardModelPres

interface OnCardClickListener {
    fun onClick(item: CardModelPres)
    fun onLongClick(item: CardModelPres)
    fun onAddCard()
}