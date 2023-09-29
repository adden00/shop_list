package com.adden00.testtaskunisafe.app

import android.app.Application
import android.content.Context
import com.adden00.testtaskunisafe.app.di.AppComponent
import com.adden00.testtaskunisafe.app.di.DaggerAppComponent

class App : Application() {
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }
}
fun Context.getAppComponent(): AppComponent = (this.applicationContext as App).appComponent