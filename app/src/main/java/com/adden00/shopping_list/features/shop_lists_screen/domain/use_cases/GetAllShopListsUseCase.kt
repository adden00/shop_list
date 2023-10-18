package com.adden00.shopping_list.features.shop_lists_screen.domain.use_cases

import android.content.SharedPreferences
import com.adden00.shopping_list.core.Constants
import com.adden00.shopping_list.core.TokenIsNullException
import com.adden00.shopping_list.features.shop_lists_screen.domain.models.ShopListModelDomain
import com.adden00.shopping_list.features.shop_lists_screen.domain.repository.ShopListsRepository
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