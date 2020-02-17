package com.bonusgaming.battleofmindskotlin.web

import com.bonusgaming.battleofmindskotlin.App
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebRepo @Inject constructor() {

    @Inject
    lateinit var retrofit: Retrofit
    var imagesUrlApi: ImagesUrlApi

    init {
        App.appComponent.inject(this)
        imagesUrlApi = retrofit.create(ImagesUrlApi::class.java)
    }


}