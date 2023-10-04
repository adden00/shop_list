package com.adden00.testtaskunisafe.features.shop_list_items_screen.domain.use_cases

import com.adden00.testtaskunisafe.core.FalseSuccessResponseException
import com.adden00.testtaskunisafe.features.shop_list_items_screen.domain.ShopListItemsRepository
import com.adden00.testtaskunisafe.features.shop_list_items_screen.domain.models.ShopListItemModelDomain
import javax.inject.Inject

class CrossItemUseCase  @Inject constructor(private val repository: ShopListItemsRepository) {
    suspend operator fun invoke(listId: Int, itemId: Int): List<ShopListItemModelDomain> {
            val success = repository.crossItem(itemId)
            if(success) {
                return repository.getAllItems(listId)
            }
            else
                throw FalseSuccessResponseException("cross result not success!")
        }
}