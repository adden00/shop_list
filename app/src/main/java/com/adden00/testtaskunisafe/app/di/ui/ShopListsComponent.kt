package com.adden00.testtaskunisafe.app.di.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adden00.testtaskunisafe.app.di.AppComponent
import com.adden00.testtaskunisafe.app.di.ScreenScope
import com.adden00.testtaskunisafe.app.di.ViewModelKey
import com.adden00.testtaskunisafe.core.ViewModelFactory
import com.adden00.testtaskunisafe.features.shop_lists_screen.presentation.ShopListsFragment
import com.adden00.testtaskunisafe.features.shop_lists_screen.presentation.ShopListsViewModel
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap

@ScreenScope
@Component(
    dependencies = [AppComponent::class],
    modules = [ShopListsModule::class]
)
interface ShopListsComponent {
    fun inject(fragment: ShopListsFragment)
    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): ShopListsComponent
    }
}

@Module
interface ShopListsModule {
    @Binds
    fun bindViewModelFactory(impl: ViewModelFactory): ViewModelProvider.Factory

    @IntoMap
    @ViewModelKey(ShopListsViewModel::class)
    @Binds
    fun bindShopListsViewModel(impl: ShopListsViewModel): ViewModel

}