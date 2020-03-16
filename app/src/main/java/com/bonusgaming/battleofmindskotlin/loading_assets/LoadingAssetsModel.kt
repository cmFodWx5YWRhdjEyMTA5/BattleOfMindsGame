package com.bonusgaming.battleofmindskotlin.loading_assets

import android.os.Handler
import com.bonusgaming.battleofmindskotlin.BuildConfig
import com.bonusgaming.battleofmindskotlin.db.StickerDao
import com.bonusgaming.battleofmindskotlin.db.StickerEntry
import com.bonusgaming.battleofmindskotlin.web.Item
import com.bonusgaming.battleofmindskotlin.web.WebRepo
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

//Реализуем Model для MVVM, работа с бд и интернетом
@Singleton
class LoadingAssetsModel @Inject constructor(val stickersDao: StickerDao,
                                             val webRepo: WebRepo) {

    //strong reference for ImageTarget
    val listImageTarget = mutableMapOf<String, ImageTarget>()

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
        stickersDao.insertAll(sticker)
    }

    fun getHashStickersList() = stickersDao.getHashStickersList()

    //скачиваем картинку через Picasso
    fun downloadAndSaveImage(
            url: String,
            fileName: String,
            onDownload: () -> Unit,
            onException: (url: String) -> Unit
    ) {
        val imageTarget = ImageTarget(fileName, onDownload, onException)
        listImageTarget[url] = imageTarget
        Picasso.get().load(url)
                .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                .into(imageTarget)
    }

    /*
    повторить загрузку через afterMilliseconds
    миллисекунд для url, для задержки используется Handler
    */
    fun retryDownload(url: String, afterMilliseconds: Long) {
        val handler = Handler()
        handler.postDelayed({
            listImageTarget[url]?.let {
                Picasso.get().load(url)
                        .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                        .into(it)
            }
        }, afterMilliseconds)
    }

    fun isAvatarCreated() = FirebaseAuth.getInstance().currentUser != null
}
