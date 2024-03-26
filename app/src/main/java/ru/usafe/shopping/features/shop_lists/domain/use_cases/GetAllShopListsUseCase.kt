package ru.usafe.shopping.features.shop_lists.domain.use_cases

import ru.usafe.shopping.core.AppSettings
import ru.usafe.shopping.core.TokenIsNullException
import ru.usafe.shopping.features.shop_lists.domain.models.ShopListModelDomain
import ru.usafe.shopping.features.shop_lists.domain.repository.ShopListsRepository
import javax.inject.Inject

class GetAllShopListsUseCase @Inject constructor(
    private val repository: ShopListsRepository,
    private val appSettings: AppSettings

) {
    suspend operator fun invoke(): List<ShopListModelDomain> {
        val token = appSettings.activeToken
        if (token == null) {
            throw TokenIsNullException("token is null!")
        }
        else {
            return repository.getAllShopLists(token)
        }
    }
}