package com.bonusgaming.battleofmindskotlin.core.main.mediator

import android.content.Context
import com.bonusgaming.battleofmindskotlin.core.main.PathProvider

interface PathFactory {
    fun provide(): PathProvider
}