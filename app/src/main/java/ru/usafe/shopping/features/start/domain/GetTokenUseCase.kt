package ru.usafe.shopping.features.start.domain

import android.content.SharedPreferences
import ru.usafe.shopping.core.Constants
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(
    private val prefs: SharedPreferences
) {
    operator fun invoke(): String? {
        return prefs.getString(Constants.TOKEN_KEY, null)
    }
}