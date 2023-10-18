package com.adden00.shopping_list.app.di

import android.content.Context
import android.content.SharedPreferences
import com.adden00.shopping_list.core.Constants

import dagger.Module
import dagger.Provides

@Module
class SharedPrefModule {
    @Provides
    fun provideSharedPrefs(context: Context): SharedPreferences =
        context.getSharedPreferences(Constants.PREFS_KEY, Context.MODE_PRIVATE)
}