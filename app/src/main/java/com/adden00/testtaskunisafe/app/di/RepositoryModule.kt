package com.adden00.testtaskunisafe.app.di

import com.adden00.testtaskunisafe.features.shop_list_screen.data.ShopListApiClient
import com.adden00.testtaskunisafe.features.shop_list_screen.data.repository.ShopListRepositoryImpl
import com.adden00.testtaskunisafe.features.shop_list_screen.domain.repository.ShopListRepository
import com.adden00.testtaskunisafe.features.start_screen.data.AuthApiClient
import com.adden00.testtaskunisafe.features.start_screen.data.AuthRepositoryImpl
import com.adden00.testtaskunisafe.features.start_screen.domain.AuthRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {
    @Provides
    fun provideShopListRepo(api: ShopListApiClient): ShopListRepository =
        ShopListRepositoryImpl(api)

    @Provides
    fun provideAuthRepo(api: AuthApiClient): AuthRepository =
        AuthRepositoryImpl(api)
}