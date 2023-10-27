package ru.usafe.shopping.features.cards.data

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.usafe.shopping.features.cards.data.models.GetCardsResponse
import ru.usafe.shopping.features.cards.data.models.UpdateCardsResponse

interface CardsApiClient {
    @GET("GetCards")
    suspend fun getAllCards(
        @Query("key") token: String
    ): GetCardsResponse

    @POST("BlockCard")
    suspend fun removeCard(
        @Query("key") token: String,
        @Query("card_id") id: Int,
    ): UpdateCardsResponse

    @POST("AddCard")
    suspend fun addCard(
        @Query("card_name") cardName: String,
        @Query("card_code") cardCode: String,
        @Query("card_qr") cardQr: String,
        @Query("key") token: String,
        @Query("card_barcode") cardBarcode: String,
        @Query("card_hex") cardHex: String
    ): UpdateCardsResponse

    @POST("ChangeCard")
    suspend fun updateCard(
        @Query("card_name") cardName: String,
        @Query("card_id") cardId: Int,
        @Query("card_code") cardCode: String,
        @Query("card_qr") cardQr: String,
        @Query("key") token: String,
        @Query("card_barcode") cardBarcode: String,
        @Query("card_hex") cardHex: String
    ): UpdateCardsResponse
}