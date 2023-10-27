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
import ru.usafe.shopping.features.cards.presentation.CardsViewModel
import ru.usafe.shopping.features.cards.presentation.QrScannerFragment


@ScreenScope
@Component(
    dependencies = [AppComponent::class],
    modules = [QrScannerModule::class]
)
interface QrScannerComponent {
    fun inject(fragment: QrScannerFragment)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): QrScannerComponent
    }
}

@Module
interface QrScannerModule {
    @Binds
    fun bindViewModelFactory(impl: ViewModelFactory): ViewModelProvider.Factory

    @IntoMap
    @ViewModelKey(CardsViewModel::class)
    @Binds
    fun bindListItemsViewModel(impl: CardsViewModel): ViewModel

}