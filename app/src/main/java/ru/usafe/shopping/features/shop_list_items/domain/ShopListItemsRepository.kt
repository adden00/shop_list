package ru.usafe.shopping.features.shop_list_items.domain

import ru.usafe.shopping.features.shop_list_items.domain.models.ShopListItemModelDomain

interface ShopListItemsRepository {
    suspend fun getAllItems(listId: Int): List<ShopListItemModelDomain>
    suspend fun addNewItem(listId: Int, itemName: String): Boolean
    suspend fun crossItem(itemId: Int): Boolean
    suspend fun removeItem(listId: Int, itemId: Int): Boolean
    suspend fun updateItem(itemId: Int, newName: String): Boolean
    suspend fun moveItem(startId: Int, toId: Int, listId: Int): Boolean
}