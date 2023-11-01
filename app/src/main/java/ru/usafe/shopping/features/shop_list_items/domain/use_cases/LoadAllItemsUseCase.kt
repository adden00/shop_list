package ru.usafe.shopping.features.shop_list_items.domain.use_cases

import ru.usafe.shopping.features.shop_list_items.domain.ShopListItemsRepository
import ru.usafe.shopping.features.shop_list_items.domain.models.ShopListItemModelDomain
import javax.inject.Inject

class LoadAllItemsUseCase @Inject constructor(private val repository: ShopListItemsRepository) {
    suspend operator fun invoke(listId: Int): List<ShopListItemModelDomain> =
        repository.getAllItems(listId)
}