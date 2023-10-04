package com.adden00.testtaskunisafe.features.shop_list_items_screen.domain

import com.adden00.testtaskunisafe.features.shop_list_items_screen.domain.models.ShopListItemModelDomain

interface ShopListItemsRepository {
    suspend fun getAllItems(listId: Int): List<ShopListItemModelDomain>
    suspend fun addNewItem(listId: Int, itemName: String): Boolean
    suspend fun crossItem(itemId: Int): Boolean
    suspend fun removeItem(listId: Int, itemId: Int): Boolean
}