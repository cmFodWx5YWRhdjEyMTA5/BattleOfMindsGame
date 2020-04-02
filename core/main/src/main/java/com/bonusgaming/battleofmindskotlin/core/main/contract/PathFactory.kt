package com.bonusgaming.battleofmindskotlin.core.main.contract

import com.bonusgaming.battleofmindskotlin.core.main.PathProvider

interface PathFactory {
    fun provide(): PathProvider
}
