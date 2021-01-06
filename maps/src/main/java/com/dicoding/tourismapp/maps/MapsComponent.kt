package com.dicoding.tourismapp.maps

import android.content.Context
import com.dicoding.tourismapp.di.MapsModuleDependencies
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [MapsModuleDependencies::class])
interface MapsComponent {

    fun inject(activity: MapsActivity)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(mapsModuleDependencies: MapsModuleDependencies): Builder
        fun build(): MapsComponent
    }

}