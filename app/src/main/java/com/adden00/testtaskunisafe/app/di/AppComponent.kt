package com.adden00.testtaskunisafe.app.di

import android.content.Context
import android.content.SharedPreferences
import com.adden00.testtaskunisafe.core.utills.InternetChecker
import com.adden00.testtaskunisafe.features.auth_screen.domain.AuthRepository
import com.adden00.testtaskunisafe.features.shop_list_items_screen.domain.ShopListItemsRepository
import com.adden00.testtaskunisafe.features.shop_lists_screen.domain.repository.ShopListsRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, RepositoryModule::class, SharedPrefModule::class])
interface AppComponent {
    fun shopListRepository(): ShopListsRepository
    fun authRepository(): AuthRepository
    fun shopListItemsRepository(): ShopListItemsRepository
    fun sharedPref(): SharedPreferences
    fun internetChecker(): InternetChecker



    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}