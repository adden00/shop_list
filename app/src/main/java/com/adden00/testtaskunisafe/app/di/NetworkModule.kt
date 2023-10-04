package com.adden00.testtaskunisafe.app.di

import com.adden00.testtaskunisafe.core.Constants
import com.adden00.testtaskunisafe.features.auth_screen.data.AuthApiClient
import com.adden00.testtaskunisafe.features.shop_list_items_screen.data.ShopListItemApiClient
import com.adden00.testtaskunisafe.features.shop_lists_screen.data.ShopListApiClient
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideShopListApiClient(client: OkHttpClient): ShopListApiClient {
        val json = Json { ignoreUnknownKeys = true }
        return Retrofit.Builder()
            .client(client)
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(
                @OptIn(ExperimentalSerializationApi::class)
                json.asConverterFactory("application/json".toMediaType())
            )
            .build()
            .create(ShopListApiClient::class.java)
    }

    @Provides
    @Singleton
    fun provideShopListItemsApiClient(client: OkHttpClient): ShopListItemApiClient {
        val json = Json { ignoreUnknownKeys = true }
        return Retrofit.Builder()
            .client(client)
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(
                @OptIn(ExperimentalSerializationApi::class)
                json.asConverterFactory("application/json".toMediaType())
            )
            .build()
            .create(ShopListItemApiClient::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthApiClient(client: OkHttpClient): AuthApiClient {
        val json = Json { ignoreUnknownKeys = true }
        return Retrofit.Builder()
            .client(client)
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(
                @OptIn(ExperimentalSerializationApi::class)
                json.asConverterFactory("application/json".toMediaType())
            )
            .build()
            .create(AuthApiClient::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient() =
        OkHttpClient.Builder()
            .callTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(5, TimeUnit.SECONDS)
            .build()
}