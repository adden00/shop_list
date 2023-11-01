package ru.usafe.shopping.features.shop_lists.domain.repository

import ru.usafe.shopping.features.shop_lists.domain.models.ShopListModelDomain

interface ShopListsRepository {
    suspend fun createShopList(name: String, token: String): Boolean
    suspend fun getAllShopLists(token: String): List<ShopListModelDomain>
    suspend fun removeShopList(token: String, listId: Int): Boolean
}