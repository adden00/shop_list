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
import ru.usafe.shopping.features.auth_screen.presentation.AuthFragment
import ru.usafe.shopping.features.auth_screen.presentation.AuthViewModel

@ScreenScope
@Component(
    dependencies = [AppComponent::class],
    modules = [AuthModule::class]
)
interface AuthComponent {
    fun inject(fragment: AuthFragment)
    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): AuthComponent
    }
}

@Module
interface AuthModule {

    @Binds
    fun bindViewModelFactory(impl: ViewModelFactory): ViewModelProvider.Factory

    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    @Binds
    fun bindAuthViewModel(impl: AuthViewModel): ViewModel

}