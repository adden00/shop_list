package ru.usafe.shopping.features.shop_lists_screen.domain.use_cases

import android.content.SharedPreferences
import ru.usafe.shopping.core.Constants
import javax.inject.Inject

class CopyAccountIdUseCase @Inject constructor(private val prefs: SharedPreferences) {
    operator fun invoke(copy: (String) -> Unit) {
        val token = prefs.getString(Constants.TOKEN_KEY, null)
        token?.let {
            copy(it)
        }
    }
}