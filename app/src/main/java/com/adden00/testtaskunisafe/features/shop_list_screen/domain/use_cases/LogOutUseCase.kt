package com.adden00.testtaskunisafe.features.shop_list_screen.domain.use_cases

import android.content.SharedPreferences
import com.adden00.testtaskunisafe.core.Constants
import javax.inject.Inject

class LogOutUseCase @Inject constructor(private val prefs: SharedPreferences) {
    operator fun invoke() {
        prefs.edit().remove(Constants.TOKEN_KEY).apply()
    }
}