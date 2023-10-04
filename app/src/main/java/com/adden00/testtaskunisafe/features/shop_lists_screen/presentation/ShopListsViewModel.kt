package com.adden00.testtaskunisafe.features.shop_lists_screen.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adden00.testtaskunisafe.features.shop_lists_screen.domain.use_cases.CopyAccountIdUseCase
import com.adden00.testtaskunisafe.features.shop_lists_screen.domain.use_cases.CreateShopListUseCase
import com.adden00.testtaskunisafe.features.shop_lists_screen.domain.use_cases.GetAllShopListsUseCase
import com.adden00.testtaskunisafe.features.shop_lists_screen.domain.use_cases.LogOutUseCase
import com.adden00.testtaskunisafe.features.shop_lists_screen.domain.use_cases.RemoveShopListUseCase
import com.adden00.testtaskunisafe.features.shop_lists_screen.presentation.mvi.ShopListEffect
import com.adden00.testtaskunisafe.features.shop_lists_screen.presentation.mvi.ShopListEvent
import com.adden00.testtaskunisafe.features.shop_lists_screen.presentation.mvi.ShopListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShopListsViewModel @Inject constructor(

    private val getAllShopListsUseCase: GetAllShopListsUseCase,
    private val createShopListUseCase: CreateShopListUseCase,
    private val logOutUseCase: LogOutUseCase,
    private val copyAccountIdUseCase: CopyAccountIdUseCase,
    private val removeShopListUseCase: RemoveShopListUseCase,
) : ViewModel() {

    private val _shopListState = MutableStateFlow(ShopListState())
    val shopListState: StateFlow<ShopListState> = _shopListState.asStateFlow()

    private val _shopListEffect = MutableStateFlow<ShopListEffect>(ShopListEffect.Waiting)
    val shopListEffect: StateFlow<ShopListEffect> get() = _shopListEffect.asStateFlow()

    init {
        newEvent(ShopListEvent.GetAllShopLists)
    }

    fun newEvent(event: ShopListEvent) {
        when (event) {
            is ShopListEvent.CreateShopList -> {
                _shopListState.update { it.copy(isUpdating = true) }
                viewModelScope.launch {
                    try {
                        val newList = createShopListUseCase(event.name)
                        _shopListState.update { it.copy(list = newList.map { item -> item.toPresentation() }) }
                    } catch (e: Exception) {
                        Log.d("my_log", e.toString())
                        _shopListEffect.update { ShopListEffect.InternetError }
                    }
                    finally {
                        _shopListEffect.update { ShopListEffect.Waiting }
                        _shopListState.update { it.copy(isUpdating = false) }
                    }
                }
            }

            is ShopListEvent.GetAllShopLists -> {
                _shopListState.update { it.copy(isLoading = true) }
                viewModelScope.launch {
                    try {
                        val result = getAllShopListsUseCase()
                        _shopListState.update { it.copy(list = result.map { item -> item.toPresentation() }) }

                    } catch (e: Exception) {
                        Log.d("my_log", e.toString())
                        _shopListEffect.update { ShopListEffect.InternetError }
                    } finally {
                        _shopListState.update { it.copy(isLoading = false) }
                        _shopListEffect.update { ShopListEffect.Waiting }

                    }
                }
            }

            is ShopListEvent.LogOut -> {
                logOutUseCase()
                _shopListEffect.update { ShopListEffect.LogOut }
                _shopListEffect.update { ShopListEffect.Waiting }

            }

            is ShopListEvent.CopyShopListId -> {
                copyAccountIdUseCase(event.copy)
            }

            is ShopListEvent.RemoveShopList -> {
                _shopListState.update { it.copy(isUpdating = true) }
                viewModelScope.launch {
                    try {
                        val newList = removeShopListUseCase(event.listId)
                        _shopListState.update { it.copy(list = newList.map { item -> item.toPresentation() }) }
                    } catch (e: Exception) {
                        Log.d("my_log", e.toString())
                        _shopListEffect.update { ShopListEffect.InternetError }
                    }
                    finally {
                        _shopListEffect.update { ShopListEffect.Waiting }
                        _shopListState.update { it.copy(isUpdating = false) }
                    }
                }
            }
        }

    }

}