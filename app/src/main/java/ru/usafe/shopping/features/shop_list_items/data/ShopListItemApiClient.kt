package ru.usafe.shopping.features.shop_list_items.data

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.usafe.shopping.features.shop_list_items.data.models.responses.GetListResponse
import ru.usafe.shopping.features.shop_list_items.data.models.responses.ItemChangeResponse

interface ShopListItemApiClient {
    @GET("GetShoppingList")
    suspend fun getAllItems(
        @Query("list_id") listId: Int
    ): GetListResponse

    @POST ("AddToShoppingList")
    suspend fun addItemToList(
        @Query("id") listId: Int,
        @Query("value") itemName: String,
        @Query("n") count: Int = 1
    ): ItemChangeResponse

    @POST("CrossItOff")
    suspend fun crossItem(
        @Query("id") itemId: Int,
    ): ItemChangeResponse

    @POST("RemoveFromList")
    suspend fun removeFromList(
        @Query("list_id") listId: Int,
        @Query("item_id") itemId: Int
    ): ItemChangeResponse

    @POST("UpdateShoppingList")
    suspend fun updateItem(
        @Query("id") itemId: Int,
        @Query("value") newName: String,
        @Query("n") count: Int = 1
    ): ItemChangeResponse

    @POST("MoveItem")
    suspend fun moveItem(
        @Query("startId") startId: Int,
        @Query("toId") toId: Int,
        @Query("list_id") listId: Int,
        @Query("id") id: Int = startId,
    ): ItemChangeResponse
}