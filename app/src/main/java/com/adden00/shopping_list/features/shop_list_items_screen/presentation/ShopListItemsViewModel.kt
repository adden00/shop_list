package com.adden00.shopping_list.features.shop_list_items_screen.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adden00.shopping_list.core.Constants
import com.adden00.shopping_list.features.shop_list_items_screen.domain.use_cases.AddNewItemUseCase
import com.adden00.shopping_list.features.shop_list_items_screen.domain.use_cases.CrossItemUseCase
import com.adden00.shopping_list.features.shop_list_items_screen.domain.use_cases.LoadAllItemsUseCase
import com.adden00.shopping_list.features.shop_list_items_screen.domain.use_cases.RemoveItemUseCase
import com.adden00.shopping_list.features.shop_list_items_screen.presentation.mvi.ShopListItemsEffect
import com.adden00.shopping_list.features.shop_list_items_screen.presentation.mvi.ShopListItemsEvent
import com.adden00.shopping_list.features.shop_list_items_screen.presentation.mvi.ShopListItemsState
import com.adden00.shopping_list.features.shop_list_items_screen.presentation.utills.toPresentation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShopListItemsViewModel @Inject constructor(
    private val addNewItemUseCase: AddNewItemUseCase,
    private val loadAllItemsUseCase: LoadAllItemsUseCase,
    private val removeItemUseCase: RemoveItemUseCase,
    private val crossItemUseCase: CrossItemUseCase
) : ViewModel() {
    private var isSubscribed = false
    private val _shopListItemsState = MutableStateFlow(ShopListItemsState())
    val shopListItemsState: StateFlow<ShopListItemsState> get() = _shopListItemsState.asStateFlow()

    private val _shopListItemsEffect =
        MutableStateFlow<ShopListItemsEffect>(ShopListItemsEffect.Waiting)
    val shopListItemsEffect: StateFlow<ShopListItemsEffect> get() = _shopListItemsEffect.asStateFlow()

    fun newEvent(event: ShopListItemsEvent) {
        when (event) {
            is ShopListItemsEvent.AddNewItem -> {
                _shopListItemsState.update { it.copy(isUpdating = true) }
                viewModelScope.launch {
                    try {
                        val newList =
                            addNewItemUseCase(event.listId, event.name).map { it.toPresentation() }
                        _shopListItemsState.update { it.copy(list = newList) }
                    } catch (e: Exception) {
                        _shopListItemsEffect.update { ShopListItemsEffect.ShowInternetError }
                    } finally {
                        _shopListItemsState.update { it.copy(isUpdating = false) }
                        _shopListItemsEffect.update { ShopListItemsEffect.Waiting }
                    }
                }
            }


            is ShopListItemsEvent.LoadAllItems -> {
                _shopListItemsState.update { it.copy(isLoading = true) }
                viewModelScope.launch {
                    try {
                        val list = loadAllItemsUseCase(event.listId).map { it.toPresentation() }
                        _shopListItemsState.update { it.copy(list = list) }
                    } catch (e: Exception) {
                        _shopListItemsEffect.update { ShopListItemsEffect.ShowInternetError }
                    } finally {
                        _shopListItemsState.update { it.copy(isLoading = false) }
                        _shopListItemsEffect.update { ShopListItemsEffect.Waiting }
                    }
                }
            }

            is ShopListItemsEvent.RemoveItem -> {
                _shopListItemsState.update { it.copy(isUpdating = true) }
                viewModelScope.launch {
                    try {
                        val newList = removeItemUseCase(
                            event.listId,
                            event.itemId
                        ).map { it.toPresentation() }
                        _shopListItemsState.update { it.copy(list = newList) }
                    } catch (e: Exception) {
                        _shopListItemsEffect.update { ShopListItemsEffect.ShowInternetError }
                    } finally {
                        _shopListItemsState.update { it.copy(isUpdating = false) }
                        _shopListItemsEffect.update { ShopListItemsEffect.Waiting }
                    }
                }
            }

            is ShopListItemsEvent.CrossItem -> {
                _shopListItemsState.update { it.copy(isUpdating = true) }
                viewModelScope.launch {
                    try {
                        val newList =
                            crossItemUseCase(event.listId, event.itemId).map { it.toPresentation() }
                        _shopListItemsState.update { it.copy(list = newList) }
                    } catch (e: Exception) {
                        _shopListItemsEffect.update { ShopListItemsEffect.ShowInternetError }
                    } finally {
                        _shopListItemsState.update { it.copy(isUpdating = false) }
                        _shopListItemsEffect.update { ShopListItemsEffect.Waiting }
                    }
                }
            }

            is ShopListItemsEvent.SubscribeOnUpdating -> {
                isSubscribed = true
                Log.d("UpdateList", "started")
                viewModelScope.launch(Dispatchers.IO) {
                    while (isSubscribed) {
                        try {
                            val list = loadAllItemsUseCase(event.listId).map { it.toPresentation() }
                            _shopListItemsState.update { it.copy(list = list) }
                            Log.d("UpdateList", "updated")
                        } catch (_: Exception) {
                        }
                        delay(Constants.UPDATE_DELAY)
                    }
                }
            }

            is ShopListItemsEvent.UnSubscribeOnUpdating -> {
                isSubscribed = false
                Log.d("UpdateList", "stopped")
            }
        }
    }
}