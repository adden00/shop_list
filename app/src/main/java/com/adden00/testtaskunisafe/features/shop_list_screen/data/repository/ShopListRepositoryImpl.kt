package com.adden00.testtaskunisafe.features.shop_list_screen.data.repository

import com.adden00.testtaskunisafe.features.shop_list_screen.data.ShopListApiClient
import com.adden00.testtaskunisafe.features.shop_list_screen.data.toDomain
import com.adden00.testtaskunisafe.features.shop_list_screen.domain.models.ShopListModelDomain
import com.adden00.testtaskunisafe.features.shop_list_screen.domain.repository.ShopListRepository

class ShopListRepositoryImpl(private val api: ShopListApiClient): ShopListRepository {
    override suspend fun createKey(): String {
        return api.createTestKey()
    }

    override suspend fun createShopList(name: String, token: String): Boolean {
        val response = api.createNewShopList(token, name)
        return response.isSuccess

    }

    override suspend fun getAllShopLists(token: String): List<ShopListModelDomain> {
        val response = api.getAllShopLists(token)
        if(response.isSuccess) {
            return response.shopLists.map {it.toDomain()}
        }
        else
            throw Exception("response was not success!")
    }
}