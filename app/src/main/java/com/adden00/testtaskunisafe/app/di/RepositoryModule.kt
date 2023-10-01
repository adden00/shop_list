package com.adden00.testtaskunisafe.app.di

import com.adden00.testtaskunisafe.features.shop_list_screen.data.ShopListApiClient
import com.adden00.testtaskunisafe.features.shop_list_screen.data.repository.ShopListRepositoryImpl
import com.adden00.testtaskunisafe.features.shop_list_screen.domain.repository.ShopListRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {
    @Provides
    fun provideCurrencyRepo(api: ShopListApiClient): ShopListRepository =
        ShopListRepositoryImpl(api)
}