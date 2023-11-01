package ru.usafe.shopping.features.shop_list_items.domain.use_cases

import ru.usafe.shopping.core.FalseSuccessResponseException
import ru.usafe.shopping.features.shop_list_items.domain.ShopListItemsRepository
import ru.usafe.shopping.features.shop_list_items.domain.models.ShopListItemModelDomain
import javax.inject.Inject

class AddNewItemUseCase  @Inject constructor(private val repository: ShopListItemsRepository) {
    suspend operator fun invoke(listId: Int, name: String): List<ShopListItemModelDomain> {
            val success = repository.addNewItem(listId, name)
            if(success) {
                return repository.getAllItems(listId)
            }
            else
                throw FalseSuccessResponseException("creating result not success!")
        }
}