package com.adden00.shopping_list.app.di.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adden00.shopping_list.app.di.AppComponent
import com.adden00.shopping_list.app.di.ScreenScope
import com.adden00.shopping_list.app.di.ViewModelKey
import com.adden00.shopping_list.core.ViewModelFactory
import com.adden00.shopping_list.features.start_screen.presentation.StartFragment
import com.adden00.shopping_list.features.start_screen.presentation.StartViewModel
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap

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