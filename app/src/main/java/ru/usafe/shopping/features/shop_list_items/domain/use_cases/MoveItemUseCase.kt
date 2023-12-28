package ru.usafe.shopping.features.shop_list_items.domain.use_cases

import ru.usafe.shopping.core.FalseSuccessResponseException
import ru.usafe.shopping.features.shop_list_items.domain.ShopListItemsRepository
import ru.usafe.shopping.features.shop_list_items.domain.models.ShopListItemModelDomain
import javax.inject.Inject

class MoveItemUseCase @Inject constructor(private val repository: ShopListItemsRepository) {
    suspend operator fun invoke(
        startId: Int,
        toId: Int,
        listId: Int
    ): List<ShopListItemModelDomain> {
        val success = repository.moveItem(startId, toId, listId)
        if (success) {
            return repository.getAllItems(listId)
        } else
            throw FalseSuccessResponseException("cross result not success!")
    }
}