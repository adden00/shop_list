package com.adden00.testtaskunisafe.features.start_screen.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adden00.testtaskunisafe.features.start_screen.domain.use_cases.CreateTokenUseCase
import com.adden00.testtaskunisafe.features.start_screen.domain.use_cases.GetTokenUseCase
import com.adden00.testtaskunisafe.features.start_screen.domain.use_cases.LogInUseCase
import com.adden00.testtaskunisafe.features.start_screen.presentation.mvi.AuthScreenEffect
import com.adden00.testtaskunisafe.features.start_screen.presentation.mvi.AuthScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

class StartViewModel @Inject constructor(
    private val createTokenUseCase: CreateTokenUseCase,
    private val logInUseCase: LogInUseCase,
    private val getTokenUseCase: GetTokenUseCase
) : ViewModel() {

    private val _authScreenState = MutableStateFlow(AuthScreenState())
    val authScreenState: StateFlow<AuthScreenState> get() = _authScreenState.asStateFlow()

    private val _authEffect = MutableStateFlow<AuthScreenEffect>(AuthScreenEffect.Waiting)
    val authEffect: StateFlow<AuthScreenEffect> get() = _authEffect.asStateFlow()

    init {
        tryLogIn()
    }

    fun createNewAccount() {
        _authScreenState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                createTokenUseCase()
                _authEffect.update { AuthScreenEffect.NavigateToShopLists }
            } catch (e: Throwable) {
                _authEffect.update { AuthScreenEffect.InternetError }
            }
            finally {
                _authScreenState.update { it.copy(isLoading = false) }
                _authEffect.update { AuthScreenEffect.Waiting }

            }
        }
    }

    private fun tryLogIn() {
        val token = getTokenUseCase()
        token?.let {
            logInAccount(it)
        }
    }


    fun logInAccount(token: String) {
        _authScreenState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                val success = logInUseCase(token)
                if (success) {
                    _authEffect.update { AuthScreenEffect.NavigateToShopLists }
                } else {
                    _authEffect.update { AuthScreenEffect.WrongTokenError }
                }
            }
            catch (e: HttpException) {
                if(e.code() == 406 || e.code() == 404)
                    _authEffect.update { AuthScreenEffect.WrongTokenError }
                else throw e
            }
            catch (e: Throwable) {
                Log.d("MyLog", e.toString())
                _authEffect.update { AuthScreenEffect.InternetError }
            }
            finally {
                _authScreenState.update { it.copy(isLoading = false) }
                _authEffect.update { AuthScreenEffect.Waiting }
            }

        }
    }
}