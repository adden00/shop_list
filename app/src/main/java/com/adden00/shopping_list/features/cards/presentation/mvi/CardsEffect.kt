package com.adden00.shopping_list.features.cards.presentation.mvi


sealed interface CardsEffect {
    object Waiting : CardsEffect
    object LogOut : CardsEffect
    object InternetError : CardsEffect
    class ShowMessage(val message: String) : CardsEffect
}