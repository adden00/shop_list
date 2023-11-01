package ru.usafe.shopping.features.cards.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.usafe.shopping.core.TokenIsNullException
import ru.usafe.shopping.features.cards.domain.use_cases.AddCardUseCase
import ru.usafe.shopping.features.cards.domain.use_cases.ClearCardsUseCase
import ru.usafe.shopping.features.cards.domain.use_cases.GetAllCardsUseCase
import ru.usafe.shopping.features.cards.domain.use_cases.RemoveCardUseCase
import ru.usafe.shopping.features.cards.domain.use_cases.UpdateCardUseCase
import ru.usafe.shopping.features.cards.presentation.mvi.CardsEffect
import ru.usafe.shopping.features.cards.presentation.mvi.CardsEvent
import ru.usafe.shopping.features.cards.presentation.mvi.CardsState
import ru.usafe.shopping.features.cards.presentation.utills.toPresentation
import javax.inject.Inject

class CardsViewModel @Inject constructor(
    private val addCardUseCase: AddCardUseCase,
    private val removeCardUseCase: RemoveCardUseCase,
    private val getAllCardsUseCase: GetAllCardsUseCase,
    private val updateCardUseCase: UpdateCardUseCase,
    private val clearCardsUseCase: ClearCardsUseCase

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
                _cardsState.update { it.copy(isCreating = true) }
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
                        _cardsState.update { it.copy(isCreating = false) }
                    }
                }
            }

            is CardsEvent.UpdateCard -> {
                _cardsState.update { it.copy(isLoading = true) }
                viewModelScope.launch {
                    try {
                        val newList = updateCardUseCase(
                            event.cardName,
                            event.cardId,
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

            is CardsEvent.ClearCards -> {
                _cardsState.update { it.copy(isLoading = true) }
                viewModelScope.launch {
                    try {
                        val newList = clearCardsUseCase()
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

            is CardsEvent.SearchCards -> {
                if (event.query.isNotEmpty()) {
                    _cardsState.update { it.copy(isSearching = true) }
                    _cardsState.update {
                        it.copy(searchedList = it.cardsList.filter { cardModel ->
                            event.query.lowercase().trim() in cardModel.cardName.lowercase().trim()
                        })
                    }
                } else
                    _cardsState.update { it.copy(isSearching = false) }

            }
        }
    }
}