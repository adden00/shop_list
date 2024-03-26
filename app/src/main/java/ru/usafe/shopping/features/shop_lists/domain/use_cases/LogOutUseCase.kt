package ru.usafe.shopping.features.shop_lists.domain.use_cases

import ru.usafe.shopping.core.AppSettings
import javax.inject.Inject

class LogOutUseCase @Inject constructor(
    private val appSettings: AppSettings
) {
    operator fun invoke() {
        appSettings.activeToken = null
    }
}