package ru.usafe.shopping.features.shop_lists.domain.use_cases

import ru.usafe.shopping.core.AppSettings
import ru.usafe.shopping.core.FalseSuccessResponseException
import ru.usafe.shopping.core.TokenIsNullException
import ru.usafe.shopping.features.shop_lists.domain.models.ShopListModelDomain
import ru.usafe.shopping.features.shop_lists.domain.repository.ShopListsRepository
import javax.inject.Inject

class RemoveShopListUseCase @Inject constructor(
    private val repository: ShopListsRepository,
    private val appSettings: AppSettings

) {
    suspend operator fun invoke(listId: Int): List<ShopListModelDomain> {
        val token = appSettings.activeToken
        if (token == null) {
            throw TokenIsNullException("token is null!")
        }
        else {
            val success = repository.removeShopList(token, listId)
            if(success) {
                return repository.getAllShopLists(token)
            }
            else
                throw FalseSuccessResponseException("remove result not success!")
        }
    }
}