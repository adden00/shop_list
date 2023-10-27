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
import ru.usafe.shopping.features.start_screen.presentation.StartFragment
import ru.usafe.shopping.features.start_screen.presentation.StartViewModel

@ScreenScope
@Component(
    dependencies = [AppComponent::class],
    modules = [StartModule::class]
)
interface StartComponent {

    fun inject(fragment: StartFragment)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): StartComponent
    }
}

@Module
interface StartModule {

    @Binds
    fun bindViewModelFactory(impl: ViewModelFactory): ViewModelProvider.Factory

    @IntoMap
    @ViewModelKey(StartViewModel::class)
    @Binds
    fun bindStartViewModel(impl: StartViewModel): ViewModel
}