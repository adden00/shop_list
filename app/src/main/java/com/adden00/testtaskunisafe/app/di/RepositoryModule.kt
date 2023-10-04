package com.adden00.testtaskunisafe.app.di

import com.adden00.testtaskunisafe.features.auth_screen.data.AuthApiClient
import com.adden00.testtaskunisafe.features.auth_screen.data.AuthRepositoryImpl
import com.adden00.testtaskunisafe.features.auth_screen.domain.AuthRepository
import com.adden00.testtaskunisafe.features.shop_list_items_screen.data.ShopListItemApiClient
import com.adden00.testtaskunisafe.features.shop_list_items_screen.data.repository.ShopListItemsRepositoryImpl
import com.adden00.testtaskunisafe.features.shop_list_items_screen.domain.ShopListItemsRepository
import com.adden00.testtaskunisafe.features.shop_lists_screen.data.ShopListApiClient
import com.adden00.testtaskunisafe.features.shop_lists_screen.data.repository.ShopListsRepositoryImpl
import com.adden00.testtaskunisafe.features.shop_lists_screen.domain.repository.ShopListsRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {
    @Provides
    fun provideShopListRepo(api: ShopListApiClient): ShopListsRepository =
        ShopListsRepositoryImpl(api)

    @Provides
    fun provideAuthRepo(api: AuthApiClient): AuthRepository =
        AuthRepositoryImpl(api)

    @Provides
    fun provideListItemsRepo(api: ShopListItemApiClient): ShopListItemsRepository =
        ShopListItemsRepositoryImpl(api)
}