package ru.usafe.shopping.app

import android.app.Application
import android.content.Context
import ru.usafe.shopping.app.di.AppComponent

class App : Application() {
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = ru.usafe.shopping.app.di.DaggerAppComponent.factory().create(this)
    }
}

fun Context.getAppComponent(): AppComponent =
    (this.applicationContext as ru.usafe.shopping.app.App).appComponent