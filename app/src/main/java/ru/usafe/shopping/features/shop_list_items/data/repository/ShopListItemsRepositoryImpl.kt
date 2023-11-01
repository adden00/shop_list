package ru.usafe.shopping.features.shop_list_items.data.repository

import ru.usafe.shopping.features.shop_list_items.data.ShopListItemApiClient
import ru.usafe.shopping.features.shop_list_items.data.toDomain
import ru.usafe.shopping.features.shop_list_items.domain.ShopListItemsRepository
import ru.usafe.shopping.features.shop_list_items.domain.models.ShopListItemModelDomain

class ShopListItemsRepositoryImpl(private val api: ShopListItemApiClient) :
    ShopListItemsRepository {
    override suspend fun getAllItems(listId: Int): List<ShopListItemModelDomain> {
        val response = api.getAllItems(listId)
        if (response.isSuccess)
            return response.itemList.map { it.toDomain() }
        else
            throw RuntimeException("getAllItems response is not success!")
    }

    override suspend fun addNewItem(listId: Int, itemName: String): Boolean {
        val response = api.addItemToList(listId, itemName)
        return response.isSuccess
    }

    override suspend fun crossItem(itemId: Int): Boolean {
        val response = api.crossItem(itemId)
        return response.isSuccess
    }

    override suspend fun removeItem(listId: Int, itemId: Int): Boolean {
        val response = api.removeFromList(listId, itemId)
        return response.isSuccess
    }
}