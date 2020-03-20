package com.bonusgaming.battleofmindskotlin.core.main.mediator

import com.bonusgaming.battleofmindskotlin.core.main.ActivityUtils

interface ActivityUtilsFactory {
    fun provideActivityUtils(): ActivityUtils
}