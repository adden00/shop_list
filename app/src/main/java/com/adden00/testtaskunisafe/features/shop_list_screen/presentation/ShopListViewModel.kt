package com.adden00.testtaskunisafe.features.shop_list_screen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adden00.testtaskunisafe.features.shop_list_screen.data.repository.ShopListRepository
import com.adden00.testtaskunisafe.features.shop_list_screen.domain.use_cases.CreateTokenUseCase
import com.adden00.testtaskunisafe.features.shop_list_screen.presentation.mvi.ShopListEvent
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShopListViewModel @Inject constructor(
    private val createTokenUseCase: CreateTokenUseCase,
    private val repository: ShopListRepository
) : ViewModel() {

    fun newEvent(event: ShopListEvent) {
        when (event) {
            is ShopListEvent.GetToken -> {
                viewModelScope.launch {
                    createTokenUseCase()
                }
            }
        }

    }

}