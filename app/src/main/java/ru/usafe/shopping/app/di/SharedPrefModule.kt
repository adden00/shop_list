package ru.usafe.shopping.app.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import ru.usafe.shopping.core.Constants

@Module
class SharedPrefModule {
    @Provides
    fun provideSharedPrefs(context: Context): SharedPreferences =
        context.getSharedPreferences(Constants.PREFS_KEY, Context.MODE_PRIVATE)
}