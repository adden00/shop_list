package ru.usafe.shopping.app.di

import android.content.Context
import android.content.SharedPreferences
import dagger.BindsInstance
import dagger.Component
import ru.usafe.shopping.core.utills.InternetChecker
import ru.usafe.shopping.features.auth.domain.AuthRepository
import ru.usafe.shopping.features.cards.domain.CardsRepository
import ru.usafe.shopping.features.shop_list_items.domain.ShopListItemsRepository
import ru.usafe.shopping.features.shop_lists.domain.repository.ShopListsRepository
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, RepositoryModule::class, SharedPrefModule::class])
interface AppComponent {
    fun shopListRepository(): ShopListsRepository
    fun authRepository(): AuthRepository
    fun shopListItemsRepository(): ShopListItemsRepository
    fun cardsRepository(): CardsRepository
    fun sharedPref(): SharedPreferences
    fun internetChecker(): InternetChecker

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}