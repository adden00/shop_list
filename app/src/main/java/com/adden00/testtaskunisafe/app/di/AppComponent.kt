package com.adden00.testtaskunisafe.app.di

import android.content.Context
import android.content.SharedPreferences
import com.adden00.testtaskunisafe.features.shop_list_screen.domain.repository.ShopListRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, RepositoryModule::class, SharedPrefModule::class])
interface AppComponent {
    fun shopListRepository(): ShopListRepository
    fun sharedPref(): SharedPreferences


    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}