package ru.usafe.shopping.features.auth.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import ru.usafe.shopping.core.AppSettings
import ru.usafe.shopping.features.auth.domain.models.UserRegisterData
import ru.usafe.shopping.features.auth.domain.use_cases.LogInUseCase
import ru.usafe.shopping.features.auth.domain.use_cases.RegisterNewUserUseCase
import ru.usafe.shopping.features.auth.presentation.mvi.AuthScreenEffect
import ru.usafe.shopping.features.auth.presentation.mvi.AuthScreenState
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val logInUseCase: LogInUseCase,
    private val registerNewUserUseCase: RegisterNewUserUseCase,
    private val appSettings: AppSettings
) : ViewModel() {

    private val _authScreenState = MutableStateFlow(AuthScreenState())
    val authScreenState: StateFlow<AuthScreenState> get() = _authScreenState.asStateFlow()

    private val _authEffect = MutableStateFlow<AuthScreenEffect>(AuthScreenEffect.Waiting)
    val authEffect: StateFlow<AuthScreenEffect> get() = _authEffect.asStateFlow()

    init {
        val recentTokens = appSettings.allTokens //todo add removing from settings
        _authScreenState.update { it.copy(recentTokens = recentTokens) }
    }

    fun registerNewAccount(
        name: String,
        email: String,
//        phone: String
    ) {
        _authScreenState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                registerNewUserUseCase(
                    UserRegisterData(
                        name,
                        email,
//                    phone
                    )
                )
                _authEffect.update { AuthScreenEffect.NavigateToShopLists }
            } catch (e: Exception) {
                Log.i("auth", "exeption: $e")
                _authEffect.update { AuthScreenEffect.InternetError }
            } finally {
                _authScreenState.update { it.copy(isLoading = false) }
                _authEffect.update { AuthScreenEffect.Waiting }
            }
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