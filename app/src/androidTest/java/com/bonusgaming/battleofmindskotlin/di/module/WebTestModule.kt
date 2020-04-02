package com.bonusgaming.battleofmindskotlin.di.module

import com.bonusgaming.battleofmindskotlin.webimpl.di.module.WebModule
import dagger.Module


@Module
class WebTestModule : WebModule() {
    override fun getBaseUrlApi(): String {
        return "http://127.0.0.1:8080"
    }
}