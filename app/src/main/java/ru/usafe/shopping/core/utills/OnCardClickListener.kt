package ru.usafe.shopping.core.utills

import ru.usafe.shopping.features.cards.presentation.models.CardModelPres

interface OnCardClickListener {
    fun onClick(item: CardModelPres)
    fun onLongClick(item: CardModelPres)
    fun onAddCard()
    fun onUpdateCard(item: CardModelPres)
}