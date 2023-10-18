package com.adden00.shopping_list.features.shop_lists_screen.domain.use_cases

import android.content.SharedPreferences
import com.adden00.shopping_list.core.Constants
import javax.inject.Inject

class LogOutUseCase @Inject constructor(private val prefs: SharedPreferences) {
    operator fun invoke() {
        prefs.edit().remove(Constants.TOKEN_KEY).apply()
    }
}