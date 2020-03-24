package com.bonusgaming.battleofmindskotlin.core.main.mediator

import android.content.Context
import android.content.res.Resources

interface AppProvider {
    fun provideAppContext(): Context
    fun provideResources():Resources
}
