package com.adden00.testtaskunisafe.features.shop_list_screen.domain.repository

import com.adden00.testtaskunisafe.features.shop_list_screen.domain.models.ShopListModelDomain

interface ShopListRepository {
    suspend fun createKey(): String
    suspend fun createShopList(name: String, token: String): Boolean
    suspend fun getAllShopLists(token: String): List<ShopListModelDomain>
}