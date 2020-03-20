package com.bonusgaming.battleofmindskotlin.base_web_impl

import com.bonusgaming.battleofmindskotlin.BuildConfig
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

//интерфейс для Retrofit, постоянные параметры GET запроса берутся из app/api.properties
interface ImagesUrlApi {
    @GET("${BuildConfig.STORAGE_SERVICE}b/${BuildConfig.STORAGE_BUCKET}/o/")
    fun getListUrls(
        @Query("prefix") prefix: String
    ): Single<CloudStorageItem>
}