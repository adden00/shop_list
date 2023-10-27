package ru.usafe.shopping.app.di

import dagger.Module
import dagger.Provides
import ru.usafe.shopping.features.auth_screen.data.AuthApiClient
import ru.usafe.shopping.features.auth_screen.data.AuthRepositoryImpl
import ru.usafe.shopping.features.auth_screen.domain.AuthRepository
import ru.usafe.shopping.features.cards.data.CardsApiClient
import ru.usafe.shopping.features.cards.data.CardsRepositoryImpl
import ru.usafe.shopping.features.cards.domain.CardsRepository
import ru.usafe.shopping.features.shop_list_items_screen.data.ShopListItemApiClient
import ru.usafe.shopping.features.shop_list_items_screen.data.repository.ShopListItemsRepositoryImpl
import ru.usafe.shopping.features.shop_list_items_screen.domain.ShopListItemsRepository
import ru.usafe.shopping.features.shop_lists_screen.data.ShopListApiClient
import ru.usafe.shopping.features.shop_lists_screen.data.repository.ShopListsRepositoryImpl
import ru.usafe.shopping.features.shop_lists_screen.domain.repository.ShopListsRepository

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