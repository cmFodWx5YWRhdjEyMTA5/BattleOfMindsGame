package com.bonusgaming.battleofmindskotlin.web

import com.squareup.picasso.Picasso
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebRepo @Inject constructor(val retrofit: Retrofit) {

    var imagesUrlApi: ImagesUrlApi = retrofit.create(ImagesUrlApi::class.java)

}