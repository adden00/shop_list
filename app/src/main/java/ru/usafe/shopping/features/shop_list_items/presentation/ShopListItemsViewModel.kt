package ru.usafe.shopping.features.shop_list_items.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.usafe.shopping.core.Constants
import ru.usafe.shopping.features.shop_list_items.domain.use_cases.AddNewItemUseCase
import ru.usafe.shopping.features.shop_list_items.domain.use_cases.CrossItemUseCase
import ru.usafe.shopping.features.shop_list_items.domain.use_cases.LoadAllItemsUseCase
import ru.usafe.shopping.features.shop_list_items.domain.use_cases.MoveItemUseCase
import ru.usafe.shopping.features.shop_list_items.domain.use_cases.RemoveItemUseCase
import ru.usafe.shopping.features.shop_list_items.domain.use_cases.UpdateItemUseCase
import ru.usafe.shopping.features.shop_list_items.presentation.mvi.ShopListItemsEffect
import ru.usafe.shopping.features.shop_list_items.presentation.mvi.ShopListItemsEvent
import ru.usafe.shopping.features.shop_list_items.presentation.mvi.ShopListItemsState
import ru.usafe.shopping.features.shop_list_items.presentation.utills.toPresentation
import javax.inject.Inject

class ShopListItemsViewModel @Inject constructor(
    private val addNewItemUseCase: AddNewItemUseCase,
    private val loadAllItemsUseCase: LoadAllItemsUseCase,
    private val removeItemUseCase: RemoveItemUseCase,
    private val crossItemUseCase: CrossItemUseCase,
    private val moveItemUseCase: MoveItemUseCase,
    private val updateItemUseCase: UpdateItemUseCase

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

//            is ShopListItemsEvent.EditItem -> {
//                _shopListItemsState.update { it.copy(isUpdating = true) }
//                viewModelScope.launch {
//                    try {
//                        removeItemUseCase(
//                            event.listId,
//                            event.itemId
//                        ).map { it.toPresentation() }
//                        val newList =
//                            addNewItemUseCase(
//                                event.listId,
//                                event.newName
//                            ).map { it.toPresentation() }
//                        _shopListItemsState.update { it.copy(list = newList) }
//                    } catch (e: Exception) {
//                        _shopListItemsEffect.update { ShopListItemsEffect.ShowInternetError }
//                    } finally {
//                        _shopListItemsState.update { it.copy(isUpdating = false) }
//                        _shopListItemsEffect.update { ShopListItemsEffect.Waiting }
//                    }
//                }
//            }

            is ShopListItemsEvent.MoveItem -> {
                _shopListItemsState.update { it.copy(isUpdating = true) }
                viewModelScope.launch {
                    try {
                        val newList = moveItemUseCase(
                            event.startId,
                            event.toId,
                            event.listId,
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

            is ShopListItemsEvent.UpdateItem -> {
                _shopListItemsState.update { it.copy(isUpdating = true) }
                viewModelScope.launch {
                    try {
                        val newList =
                            updateItemUseCase(
                                event.listId,
                                event.itemId,
                                event.newName
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

            is ShopListItemsEvent.OnDrag -> {
                val list = _shopListItemsState.value.list.toMutableList()
                val movingItem = list[event.startPos]
                if (event.startPos > event.toPos) {
                    for (i in event.startPos downTo event.toPos + 1) {
                        list[i] = list[i - 1]
                    }
                    list[event.toPos] = movingItem
                } else {
                    for (i in event.startPos until event.toPos) {
                        list[i] = list[i + 1]
                    }
                    list[event.toPos] = movingItem
                }
                _shopListItemsState.update { it.copy(list = list.toList()) }
            }
        }
    }
}