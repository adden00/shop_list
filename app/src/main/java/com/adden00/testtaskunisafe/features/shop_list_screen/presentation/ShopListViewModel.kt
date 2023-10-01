package com.adden00.testtaskunisafe.features.shop_list_screen.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adden00.testtaskunisafe.features.shop_list_screen.domain.repository.ShopListRepository
import com.adden00.testtaskunisafe.features.shop_list_screen.domain.use_cases.CreateShopListUseCase
import com.adden00.testtaskunisafe.features.shop_list_screen.domain.use_cases.CreateTokenUseCase
import com.adden00.testtaskunisafe.features.shop_list_screen.domain.use_cases.GetAllShopListsUseCase
import com.adden00.testtaskunisafe.features.shop_list_screen.presentation.mvi.ShopListEvent
import com.adden00.testtaskunisafe.features.shop_list_screen.presentation.mvi.ShopListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShopListViewModel @Inject constructor(
    private val createTokenUseCase: CreateTokenUseCase,
    private val getAllShopListsUseCase: GetAllShopListsUseCase,
    private val createShopListUseCase: CreateShopListUseCase,
    private val repository: ShopListRepository
) : ViewModel() {

    private val _shopListState = MutableStateFlow(ShopListState())
    val shopListState: StateFlow<ShopListState> = _shopListState.asStateFlow()

    init {
        newEvent(ShopListEvent.GetToken)
    }

    fun newEvent(event: ShopListEvent) {
        when (event) {
            is ShopListEvent.GetToken -> {
                viewModelScope.launch {
                    createTokenUseCase()
                    newEvent(ShopListEvent.GetAllShopLists)
                }
            }

            is ShopListEvent.CreateShopList -> {
                viewModelScope.launch {
                    try {
                        val newList = createShopListUseCase(event.name)
                        _shopListState.update { it.copy(list = newList.map { item -> item.toPresentation() }) }

                    } catch (e: Exception) {
                        Log.d("my_log", e.toString())

                        //TODO обработать
                    }
                }
            }

            is ShopListEvent.GetAllShopLists -> {
                _shopListState.update { it.copy(isLoading = true) }
                viewModelScope.launch {
                    try {

                        val result = getAllShopListsUseCase()
                        _shopListState.update { it.copy(list = result.map { item -> item.toPresentation() }) }

                    } catch (e: Throwable) {
                        Log.d("my_log", e.toString())
                        //TODO обработать
                    } finally {

                        _shopListState.update { it.copy(isLoading = false) }
                    }
                }
            }
        }

    }

}