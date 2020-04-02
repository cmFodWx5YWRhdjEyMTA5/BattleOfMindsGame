package com.bonusgaming.battleofmindskotlin.webimpl


import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Интерфейс для Retrofit, постоянные параметры GET запроса берутся из api.properties
 **/
interface StickersApiRetrofit {
    @GET("${BuildConfig.STORAGE_SERVICE}b/${BuildConfig.STORAGE_BUCKET}/o/")
    fun getListUrls(
            @Query("prefix") prefix: String
    ): Single<CloudStorageItem>
}
