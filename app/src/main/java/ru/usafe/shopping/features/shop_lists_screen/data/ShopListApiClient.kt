package ru.usafe.shopping.features.shop_lists_screen.data

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.usafe.shopping.features.shop_lists_screen.data.models.ShopListResponse
import ru.usafe.shopping.features.shop_lists_screen.data.models.responses.CreateShopListsResponse
import ru.usafe.shopping.features.shop_lists_screen.data.models.responses.RemoveListResponse


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

    @POST ("RemoveShoppingList")
    suspend fun removeShopList(
        @Query ("key") token: String,
        @Query("list_id") listId: Int
    ): RemoveListResponse
}