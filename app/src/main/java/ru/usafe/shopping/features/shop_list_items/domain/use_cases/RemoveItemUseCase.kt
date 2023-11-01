package ru.usafe.shopping.features.shop_list_items.domain.use_cases

import ru.usafe.shopping.core.FalseSuccessResponseException
import ru.usafe.shopping.features.shop_list_items.domain.ShopListItemsRepository
import ru.usafe.shopping.features.shop_list_items.domain.models.ShopListItemModelDomain
import javax.inject.Inject

class RemoveItemUseCase  @Inject constructor(private val repository: ShopListItemsRepository) {
    suspend operator fun invoke(listId: Int, itemId: Int): List<ShopListItemModelDomain> {
            val success = repository.removeItem(listId, itemId)
            if(success) {
                return repository.getAllItems(listId)
            }
            else
                throw FalseSuccessResponseException("remove result not success!")
        }
}