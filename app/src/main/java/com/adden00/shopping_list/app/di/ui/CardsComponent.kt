package com.adden00.shopping_list.app.di.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adden00.shopping_list.app.di.AppComponent
import com.adden00.shopping_list.app.di.ScreenScope
import com.adden00.shopping_list.app.di.ViewModelKey
import com.adden00.shopping_list.core.ViewModelFactory
import com.adden00.shopping_list.features.cards.presentation.CardsFragment
import com.adden00.shopping_list.features.cards.presentation.CardsViewModel
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap

@ScreenScope
@Component(
    dependencies = [AppComponent::class],
    modules = [CardsModule::class]
)
interface CardsComponent {
    fun inject(fragment: CardsFragment)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): CardsComponent
    }
}

@Module
interface CardsModule {
    @Binds
    fun bindViewModelFactory(impl: ViewModelFactory): ViewModelProvider.Factory

    @IntoMap
    @ViewModelKey(CardsViewModel::class)
    @Binds
    fun bindListItemsViewModel(impl: CardsViewModel): ViewModel

}