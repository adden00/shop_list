package com.adden00.testtaskunisafe.app.di.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adden00.testtaskunisafe.app.di.AppComponent
import com.adden00.testtaskunisafe.app.di.ScreenScope
import com.adden00.testtaskunisafe.app.di.ViewModelKey
import com.adden00.testtaskunisafe.core.ViewModelFactory
import com.adden00.testtaskunisafe.features.auth_screen.presentation.AuthFragment
import com.adden00.testtaskunisafe.features.auth_screen.presentation.AuthViewModel
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap

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
    fun bindMessengerViewModel(impl: AuthViewModel): ViewModel

}