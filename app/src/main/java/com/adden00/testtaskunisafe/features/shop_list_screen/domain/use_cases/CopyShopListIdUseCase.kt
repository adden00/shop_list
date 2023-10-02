package com.adden00.testtaskunisafe.features.shop_list_screen.domain.use_cases

import android.content.SharedPreferences
import com.adden00.testtaskunisafe.core.Constants
import javax.inject.Inject

class CopyShopListIdUseCase @Inject constructor(private val prefs: SharedPreferences) {
    operator fun invoke(copy: (String) -> Unit) {
        val token = prefs.getString(Constants.TOKEN_KEY, null)
        token?.let {
            copy(it)
        }
    }

}