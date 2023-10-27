package ru.usafe.shopping.features.shop_lists_screen.data.repository

import ru.usafe.shopping.features.shop_lists_screen.data.ShopListApiClient
import ru.usafe.shopping.features.shop_lists_screen.data.toDomain
import ru.usafe.shopping.features.shop_lists_screen.domain.models.ShopListModelDomain
import ru.usafe.shopping.features.shop_lists_screen.domain.repository.ShopListsRepository

class ShopListsRepositoryImpl(private val api: ShopListApiClient): ShopListsRepository {


    override suspend fun createShopList(name: String, token: String): Boolean {
        val response = api.createNewShopList(token, name)
        return response.isSuccess

    }

    override suspend fun getAllShopLists(token: String): List<ShopListModelDomain> {
        val response = api.getAllShopLists(token)
        if (response.isSuccess) {
            return response.shopLists.map { it.toDomain() }
        } else
            throw Exception("response was not success!")
    }

    override suspend fun removeShopList(token: String, listId: Int): Boolean {
        val response = api.removeShopList(token, listId)
        return response.isSuccess
    }
}