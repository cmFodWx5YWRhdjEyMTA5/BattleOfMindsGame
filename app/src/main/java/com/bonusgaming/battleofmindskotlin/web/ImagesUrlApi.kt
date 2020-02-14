package com.bonusgaming.battleofmindskotlin.web

import com.bonusgaming.battleofmindskotlin.BuildConfig
import retrofit2.http.GET

interface ImagesUrlApi {

    @GET("b/battleofminds-2e099.appspot.com/o/")
    fun getListImageUrls() {

    }

}