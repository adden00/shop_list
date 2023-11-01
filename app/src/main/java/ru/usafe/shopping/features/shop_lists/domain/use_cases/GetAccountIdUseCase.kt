package ru.usafe.shopping.features.shop_lists.domain.use_cases

import android.content.SharedPreferences
import ru.usafe.shopping.core.Constants
import javax.inject.Inject

class GetAccountIdUseCase @Inject constructor(private val prefs: SharedPreferences) {
    operator fun invoke(): String? {
        return prefs.getString(Constants.TOKEN_KEY, null)
    }
}