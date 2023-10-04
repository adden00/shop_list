package com.adden00.testtaskunisafe.features.shop_list_items_screen.data.repository

import com.adden00.testtaskunisafe.features.shop_list_items_screen.data.ShopListItemApiClient
import com.adden00.testtaskunisafe.features.shop_list_items_screen.data.toDomain
import com.adden00.testtaskunisafe.features.shop_list_items_screen.domain.ShopListItemsRepository
import com.adden00.testtaskunisafe.features.shop_list_items_screen.domain.models.ShopListItemModelDomain

class ShopListItemsRepositoryImpl(private val api: ShopListItemApiClient): ShopListItemsRepository {
    override suspend fun getAllItems(listId: Int): List<ShopListItemModelDomain> {
        val response = api.getAllItems(listId)
        if (response.isSuccess)
            return response.itemList.map {it.toDomain()}
        else
            throw RuntimeException("getAllItems response is not success!")
    }

    override suspend fun addNewItem(listId: Int, itemName: String): Boolean {
        val response = api.addItemToList( listId, itemName)
        return response.isSuccess
        // TODO update current list
    }

}