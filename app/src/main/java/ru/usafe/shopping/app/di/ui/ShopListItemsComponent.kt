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
import ru.usafe.shopping.features.shop_list_items_screen.presentation.ShopListItemsFragment
import ru.usafe.shopping.features.shop_list_items_screen.presentation.ShopListItemsViewModel

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