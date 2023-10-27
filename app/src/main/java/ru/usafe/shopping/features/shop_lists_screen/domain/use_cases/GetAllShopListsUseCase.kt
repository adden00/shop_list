package ru.usafe.shopping.features.shop_lists_screen.domain.use_cases

import android.content.SharedPreferences
import ru.usafe.shopping.core.Constants
import ru.usafe.shopping.core.TokenIsNullException
import ru.usafe.shopping.features.shop_lists_screen.domain.models.ShopListModelDomain
import ru.usafe.shopping.features.shop_lists_screen.domain.repository.ShopListsRepository
import javax.inject.Inject

class GetAllShopListsUseCase @Inject constructor(private val repository: ShopListsRepository, private val prefs: SharedPreferences) {
    suspend operator fun invoke(): List<ShopListModelDomain> {
        val token = prefs.getString(Constants.TOKEN_KEY, null)
        if (token == null) {
            throw TokenIsNullException("token is null!")
        }
        else {
            return repository.getAllShopLists(token)
        }
    }
}