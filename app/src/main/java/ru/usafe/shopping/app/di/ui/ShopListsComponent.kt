package ru.usafe.shopping.app.di.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap
import ru.usafe.shopping.app.di.AppComponent
import ru.usafe.shopping.app.di.ScreenScope
import ru.usafe.shopping.app.di.ViewModelKey
import ru.usafe.shopping.core.ViewModelFactory
import ru.usafe.shopping.features.shop_lists.presentation.ShopListsFragment
import ru.usafe.shopping.features.shop_lists.presentation.ShopListsViewModel

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