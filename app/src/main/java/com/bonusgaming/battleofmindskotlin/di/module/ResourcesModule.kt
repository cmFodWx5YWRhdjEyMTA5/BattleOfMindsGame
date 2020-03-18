package com.bonusgaming.battleofmindskotlin.di.module

import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides

@Module
class ResourcesModule {

    @Provides
    fun getResources(context: Context): Resources {
        return context.resources
    }
}