package ru.usafe.shopping.features.start.domain

import android.content.SharedPreferences
import ru.usafe.shopping.core.AppSettings
import ru.usafe.shopping.core.Constants
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(
    private val prefs: SharedPreferences,
    private val appSettings: AppSettings
) {
    operator fun invoke(): String? {
        val oldToken = prefs.getString(Constants.TOKEN_KEY, null)
        if (!oldToken.isNullOrEmpty()) {
            appSettings.activeToken = oldToken
            appSettings.addToken(oldToken)
            prefs.edit().remove(Constants.TOKEN_KEY).apply()
        }
        return appSettings.activeToken
    }
}