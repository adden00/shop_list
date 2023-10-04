package com.adden00.testtaskunisafe.features.shop_lists_screen.domain.repository

import com.adden00.testtaskunisafe.features.shop_lists_screen.domain.models.ShopListModelDomain

interface ShopListsRepository {
    suspend fun createShopList(name: String, token: String): Boolean
    suspend fun getAllShopLists(token: String): List<ShopListModelDomain>
    suspend fun removeShopList(token: String, listId: Int): Boolean
}