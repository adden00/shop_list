package com.adden00.testtaskunisafe.features.shop_list_screen.data.repository

interface ShopListRepository {
    suspend fun createKey(): String
}