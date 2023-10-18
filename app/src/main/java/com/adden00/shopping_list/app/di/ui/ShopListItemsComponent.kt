package com.adden00.shopping_list.app.di.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adden00.shopping_list.app.di.AppComponent
import com.adden00.shopping_list.app.di.ScreenScope
import com.adden00.shopping_list.app.di.ViewModelKey
import com.adden00.shopping_list.core.ViewModelFactory
import com.adden00.shopping_list.features.shop_list_items_screen.presentation.ShopListItemsFragment
import com.adden00.shopping_list.features.shop_list_items_screen.presentation.ShopListItemsViewModel
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap

@ScreenScope
@Component(
    dependencies = [AppComponent::class],
    modules = [ShopListItemsModule::class]
)
interface ShopListItemsComponent {
    fun inject(fragment: ShopListItemsFragment)
    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): ShopListItemsComponent
    }
}

@Module
interface ShopListItemsModule {
    @Binds
    fun bindViewModelFactory(impl: ViewModelFactory): ViewModelProvider.Factory

    @IntoMap
    @ViewModelKey(ShopListItemsViewModel::class)
    @Binds
    fun bindListItemsViewModel(impl: ShopListItemsViewModel): ViewModel

}