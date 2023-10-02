package com.adden00.testtaskunisafe.features.start_screen.domain.use_cases

import android.content.SharedPreferences
import com.adden00.testtaskunisafe.core.Constants
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(
    private val prefs: SharedPreferences
) {
    operator fun invoke(): String? {
        return prefs.getString(Constants.TOKEN_KEY, null)
    }
}