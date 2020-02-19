package com.bonusgaming.battleofmindskotlin.loading_assets

import android.content.Context
import android.content.Intent
import com.bonusgaming.battleofmindskotlin.BuildConfig
import com.bonusgaming.battleofmindskotlin.db.Database
import com.bonusgaming.battleofmindskotlin.db.StickerEntry
import com.bonusgaming.battleofmindskotlin.web.Item
import com.bonusgaming.battleofmindskotlin.web.WebRepo
import com.squareup.picasso.NetworkPolicy
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoadingAssetsModel @Inject constructor() {

    //strong reference for ImageTarget
    val listImageTarget = mutableListOf<ImageTarget>()

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var database: Database

    @Inject
    lateinit var webRepo: WebRepo

    //получаем список url через rest api
    fun getFaceUrls(): Single<List<Item>> {
        return webRepo.imagesUrlApi.getListUrls(BuildConfig.PREFIX_ALL).map {
            val resultList = mutableListOf<Item>()
            it.items.forEach { item ->
                if (item.size.toInt() > 0) resultList.add(item)
            }
            resultList
        }
    }

    fun addStickerToDb(sticker: StickerEntry) {
        database.stickersDao().insertAll(sticker)
    }

    fun getHashStickersList() = database.stickersDao().getHashStickersList()

    //скачиваем картинку через Picasso
    fun downloadAndSaveImage(
        url: String,
        fileName: String,
        onDownload: () -> Unit,
        onException: () -> Unit
    ) {

        val imageTarget = ImageTarget(fileName, onDownload, onException)
        listImageTarget.add(imageTarget)
        webRepo.picasso.load(url)
            .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
            .into(imageTarget)
    }

    private fun postTaskToDownloadService() {
        context.startService(Intent(context, DownloadService::class.java))
    }

}
