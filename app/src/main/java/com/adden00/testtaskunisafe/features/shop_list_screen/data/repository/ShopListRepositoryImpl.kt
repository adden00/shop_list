package com.adden00.testtaskunisafe.features.shop_list_screen.data.repository

import com.adden00.testtaskunisafe.features.shop_list_screen.data.ShopListApiClient

class ShopListRepositoryImpl(private val api: ShopListApiClient): ShopListRepository {
    override suspend fun createKey(): String {
        return api.createTestKey()
    }
}