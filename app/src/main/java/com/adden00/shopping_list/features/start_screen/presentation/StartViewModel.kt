package com.adden00.shopping_list.features.start_screen.presentation

import androidx.lifecycle.ViewModel
import com.adden00.shopping_list.core.utills.InternetChecker
import com.adden00.shopping_list.features.start_screen.domain.GetTokenUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class StartViewModel @Inject constructor(
    private val internetChecker: InternetChecker,
    private val getTokenUseCase: GetTokenUseCase
) : ViewModel() {

    private val _startScreenState = MutableStateFlow(StartScreenState())
    val startScreenEffects: StateFlow<StartScreenState> get() = _startScreenState.asStateFlow()

    init {
        tryNavigate()
    }

    fun tryNavigate() {
        _startScreenState.update { it.copy(needToAuth = false, tokenReceived = false) }
        if (internetChecker.isOnline()) {
            _startScreenState.update { it.copy(internetIsConnected = true) }
            val token = getTokenUseCase()
            if (token == null)
                _startScreenState.update { it.copy(needToAuth = true) }
            else
                _startScreenState.update { it.copy(tokenReceived = true) }
        } else
            _startScreenState.update { it.copy(internetIsConnected = false) }
    }
}