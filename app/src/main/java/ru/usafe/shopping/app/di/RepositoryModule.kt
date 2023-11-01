package ru.usafe.shopping.app.di

import dagger.Module
import dagger.Provides
import ru.usafe.shopping.features.auth.data.AuthApiClient
import ru.usafe.shopping.features.auth.data.AuthRepositoryImpl
import ru.usafe.shopping.features.auth.domain.AuthRepository
import ru.usafe.shopping.features.cards.data.CardsApiClient
import ru.usafe.shopping.features.cards.data.CardsRepositoryImpl
import ru.usafe.shopping.features.cards.domain.CardsRepository
import ru.usafe.shopping.features.shop_list_items.data.ShopListItemApiClient
import ru.usafe.shopping.features.shop_list_items.data.repository.ShopListItemsRepositoryImpl
import ru.usafe.shopping.features.shop_list_items.domain.ShopListItemsRepository
import ru.usafe.shopping.features.shop_lists.data.ShopListApiClient
import ru.usafe.shopping.features.shop_lists.data.repository.ShopListsRepositoryImpl
import ru.usafe.shopping.features.shop_lists.domain.repository.ShopListsRepository

@Module
class RepositoryModule {
    @Provides
    fun provideShopListRepo(api: ShopListApiClient): ShopListsRepository =
        ShopListsRepositoryImpl(api)

    @Provides
    fun provideCardsRepo(api: CardsApiClient): CardsRepository =
        CardsRepositoryImpl(api)

    @Provides
    fun provideAuthRepo(api: AuthApiClient): AuthRepository =
        AuthRepositoryImpl(api)

    @Provides
    fun provideListItemsRepo(api: ShopListItemApiClient): ShopListItemsRepository =
        ShopListItemsRepositoryImpl(api)
}