package com.adden00.shopping_list.features.cards.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adden00.shopping_list.core.TokenIsNullException
import com.adden00.shopping_list.features.cards.domain.use_cases.AddCardUseCase
import com.adden00.shopping_list.features.cards.domain.use_cases.GetAllCardsUseCase
import com.adden00.shopping_list.features.cards.domain.use_cases.RemoveCardUseCase
import com.adden00.shopping_list.features.cards.presentation.mvi.CardsEffect
import com.adden00.shopping_list.features.cards.presentation.mvi.CardsEvent
import com.adden00.shopping_list.features.cards.presentation.mvi.CardsState
import com.adden00.shopping_list.features.cards.presentation.utills.toPresentation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class CardsViewModel @Inject constructor(
    private val addCardUseCase: AddCardUseCase,
    private val removeCardUseCase: RemoveCardUseCase,
    private val getAllCardsUseCase: GetAllCardsUseCase,

    ) : ViewModel() {

    private val _cardsState = MutableStateFlow(CardsState())
    val cardsState: StateFlow<CardsState> get() = _cardsState.asStateFlow()

    private val _cardsEffect = MutableStateFlow<CardsEffect>(CardsEffect.Waiting)
    val cardsEffect: StateFlow<CardsEffect> get() = _cardsEffect.asStateFlow()

    init {
        newEvent(CardsEvent.GetCards)
    }

    fun newEvent(event: CardsEvent) {
        when (event) {
            is CardsEvent.AddCard -> {
                _cardsState.update { it.copy(isLoading = true) }
                viewModelScope.launch {
                    try {
                        val newList = addCardUseCase(
                            event.cardName,
                            event.cardCode,
                            event.cardQr,
                            event.cardBarcode,
                            event.cardHex
                        )
                        _cardsState.update { it.copy(cardsList = newList.map { item -> item.toPresentation() }) }
                    } catch (e: TokenIsNullException) {
                        _cardsEffect.update { CardsEffect.LogOut }
                    } catch (e: Exception) {
                        _cardsEffect.update { CardsEffect.InternetError }
                    } finally {
                        _cardsEffect.update { CardsEffect.Waiting }
                        _cardsState.update { it.copy(isLoading = false) }
                    }
                }
            }

            is CardsEvent.GetCards -> {
                _cardsState.update { it.copy(isLoading = true) }
                viewModelScope.launch {
                    try {
                        val newList = getAllCardsUseCase()
                        _cardsState.update { it.copy(cardsList = newList.map { item -> item.toPresentation() }) }
                    } catch (e: TokenIsNullException) {
                        _cardsEffect.update { CardsEffect.LogOut }
                    } catch (e: Exception) {
                        _cardsEffect.update { CardsEffect.InternetError }
                    } finally {
                        _cardsEffect.update { CardsEffect.Waiting }
                        _cardsState.update { it.copy(isLoading = false) }
                    }
                }
            }

            is CardsEvent.RemoveCard -> {
                _cardsState.update { it.copy(isLoading = true) }
                viewModelScope.launch {
                    try {
                        val newList = removeCardUseCase(event.id)
                        _cardsState.update { it.copy(cardsList = newList.map { item -> item.toPresentation() }) }
                    } catch (e: TokenIsNullException) {
                        _cardsEffect.update { CardsEffect.LogOut }
                    } catch (e: Exception) {
                        _cardsEffect.update { CardsEffect.InternetError }
                    } finally {
                        _cardsEffect.update { CardsEffect.Waiting }
                        _cardsState.update { it.copy(isLoading = false) }
                    }
                }
            }
        }
    }
}