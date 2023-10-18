package com.adden00.shopping_list.features.start_screen.domain

import android.content.SharedPreferences
import com.adden00.shopping_list.core.Constants
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(
    private val prefs: SharedPreferences
) {
    operator fun invoke(): String? {
        return prefs.getString(Constants.TOKEN_KEY, null)
    }
}