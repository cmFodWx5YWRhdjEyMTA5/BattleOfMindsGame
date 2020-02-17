package com.bonusgaming.battleofmindskotlin.web

import com.bonusgaming.battleofmindskotlin.BuildConfig
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

//интерфейс для Retrofit

interface ImagesUrlApi {

    @GET("${BuildConfig.STORAGE_SERVICE}b/${BuildConfig.STORAGE_BUCKET}/o/")
    fun getListUrls(
        @Query("prefix") prefix: String
    ): Single<CloudStorageItem>

}