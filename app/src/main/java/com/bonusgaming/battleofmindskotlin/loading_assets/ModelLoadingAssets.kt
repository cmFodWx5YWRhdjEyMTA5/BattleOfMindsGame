package com.bonusgaming.battleofmindskotlin.loading_assets

import com.bonusgaming.battleofmindskotlin.BuildConfig
import com.bonusgaming.battleofmindskotlin.web.WebRepo
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModelLoadingAssets @Inject constructor() {

    @Inject
    lateinit var webRepo: WebRepo


    fun getFaceUrls(): Single<Map<String, String>> {
        return webRepo.imagesUrlApi.getListUrls(BuildConfig.PREFIX_ALL).map {
            val list = mutableMapOf<String, String>()
            it.items.forEach { item ->
                if (item.size.toInt() > 0)
                    list[item.mediaLink] = item.name.replace('/', '_')
            }
            list.toMap()
        }
    }
}