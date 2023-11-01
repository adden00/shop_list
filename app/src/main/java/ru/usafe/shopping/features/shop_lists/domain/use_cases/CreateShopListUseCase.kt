package ru.usafe.shopping.features.shop_lists.domain.use_cases

import android.content.SharedPreferences
import ru.usafe.shopping.core.Constants
import ru.usafe.shopping.core.FalseSuccessResponseException
import ru.usafe.shopping.core.TokenIsNullException
import ru.usafe.shopping.features.shop_lists.domain.models.ShopListModelDomain
import ru.usafe.shopping.features.shop_lists.domain.repository.ShopListsRepository
import javax.inject.Inject

class CreateShopListUseCase @Inject constructor(private val repository: ShopListsRepository, private val prefs: SharedPreferences) {
    suspend operator fun invoke(name: String): List<ShopListModelDomain> {
        val token = prefs.getString(Constants.TOKEN_KEY, null)
        if (token == null) {
            throw TokenIsNullException("token is null!")
        }
        else {
            val success = repository.createShopList(name, token)
            if(success) {
                return repository.getAllShopLists(token)
            }
            else
                throw FalseSuccessResponseException("create result not success!")
        }
    }
}