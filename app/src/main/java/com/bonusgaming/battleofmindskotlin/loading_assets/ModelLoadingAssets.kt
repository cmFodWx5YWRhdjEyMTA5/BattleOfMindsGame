package com.bonusgaming.battleofmindskotlin.loading_assets

import com.bonusgaming.battleofmindskotlin.BuildConfig
import com.bonusgaming.battleofmindskotlin.web.Item
import com.bonusgaming.battleofmindskotlin.web.WebRepo
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModelLoadingAssets @Inject constructor() {

    @Inject
    lateinit var webRepo: WebRepo

    fun getFaceUrls(): Single<List<Item>> {
        return webRepo.imagesUrlApi.getListUrls(BuildConfig.PREFIX_ALL).map { it.items }
    }
}