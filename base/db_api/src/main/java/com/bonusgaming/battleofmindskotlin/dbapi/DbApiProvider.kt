package com.bonusgaming.battleofmindskotlin.dbapi

interface DbApiProvider {
    fun provideDbApi(): DbApi
}
