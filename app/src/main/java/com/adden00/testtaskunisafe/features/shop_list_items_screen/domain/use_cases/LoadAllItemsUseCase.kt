package com.adden00.testtaskunisafe.features.shop_list_items_screen.domain.use_cases

import com.adden00.testtaskunisafe.features.shop_list_items_screen.domain.ShopListItemsRepository
import com.adden00.testtaskunisafe.features.shop_list_items_screen.domain.models.ShopListItemModelDomain
import javax.inject.Inject

class LoadAllItemsUseCase @Inject constructor(private val repository: ShopListItemsRepository) {
    suspend operator fun invoke(listId: Int): List<ShopListItemModelDomain> =
        repository.getAllItems(listId)
}