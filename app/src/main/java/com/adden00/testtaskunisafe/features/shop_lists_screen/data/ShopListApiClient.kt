package com.adden00.testtaskunisafe.features.shop_lists_screen.data

import com.adden00.testtaskunisafe.features.shop_lists_screen.data.models.ShopListResponse
import com.adden00.testtaskunisafe.features.shop_lists_screen.data.models.responses.CreateShopListsResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface ShopListApiClient {
    @GET ("GetAllMyShopLists")
    suspend fun getAllShopLists(
        @Query("key") key: String
    ): ShopListResponse


    @POST ("CreateShoppingList")
    suspend fun createNewShopList(
        @Query ("key") token: String,
        @Query ("name") name: String
    ): CreateShopListsResponse
}