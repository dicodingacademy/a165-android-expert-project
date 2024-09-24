package com.dicoding.tourismapp

import android.app.Application
import com.dicoding.tourismapp.core.di.CoreComponent
import com.dicoding.tourismapp.core.di.DaggerCoreComponent
import com.dicoding.tourismapp.di.AppComponent
import com.dicoding.tourismapp.di.DaggerAppComponent

open class MyApplication : Application() {

    private val coreComponent: CoreComponent by lazy {
        DaggerCoreComponent.factory().create(applicationContext)
    }

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(coreComponent)
    }
}