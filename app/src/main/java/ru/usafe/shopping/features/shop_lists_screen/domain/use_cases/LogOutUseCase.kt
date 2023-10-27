package ru.usafe.shopping.features.shop_lists_screen.domain.use_cases

import android.content.SharedPreferences
import ru.usafe.shopping.core.Constants
import javax.inject.Inject

class LogOutUseCase @Inject constructor(private val prefs: SharedPreferences) {
    operator fun invoke() {
        prefs.edit().remove(Constants.TOKEN_KEY).apply()
    }
}