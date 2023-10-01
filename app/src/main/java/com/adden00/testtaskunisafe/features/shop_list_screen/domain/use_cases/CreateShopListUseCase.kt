package com.adden00.testtaskunisafe.features.shop_list_screen.domain.use_cases

import android.content.SharedPreferences
import com.adden00.testtaskunisafe.core.Constants
import com.adden00.testtaskunisafe.features.shop_list_screen.domain.models.ShopListModelDomain
import com.adden00.testtaskunisafe.features.shop_list_screen.domain.repository.ShopListRepository
import javax.inject.Inject

class CreateShopListUseCase @Inject constructor(private val repository: ShopListRepository, private val prefs: SharedPreferences) {
    suspend operator fun invoke(name: String): List<ShopListModelDomain> {
        val token = prefs.getString(Constants.TOKEN_KEY, null)
        if (token == null) {
            throw Exception("token is null!")
        }
        else {
            val success = repository.createShopList(name, token)
            if(success) {
                return repository.getAllShopLists(token)
            }
            else
                throw Exception("create result not success!")
        }
    }
}