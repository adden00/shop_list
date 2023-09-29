package com.adden00.testtaskunisafe.features.shop_list_screen.data

import com.adden00.testtaskunisafe.features.shop_list_screen.data.models.ShopListResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface ShopListApiClient {
    @GET ("GetAllMyShopLists")
    suspend fun getAllShopLists(
        @Query("key") key: String
    ): ShopListResponse

    @GET ("CreateTestKey")
    suspend fun createTestKey(): String
}