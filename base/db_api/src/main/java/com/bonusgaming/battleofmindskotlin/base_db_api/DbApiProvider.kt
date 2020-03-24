package com.bonusgaming.battleofmindskotlin.base_db_api

interface DbApiProvider {
    fun provideDbApi(): DbApi
}