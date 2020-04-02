package com.bonusgaming.battleofmindskotlin.features.loading.data

import android.graphics.Bitmap
import android.os.Handler
import com.bonusgaming.battleofmindskotlin.dbapi.StickerDao
import com.bonusgaming.battleofmindskotlin.webapi.WebApi
import com.bonusgaming.battleofmindskotlin.webapi.dto.ImageTarget
import com.bonusgaming.battleofmindskotlin.core.main.PathProvider
import com.bonusgaming.battleofmindskotlin.core.main.di.scope.PerFeature
import com.bonusgaming.battleofmindskotlin.core.main.dto.Sticker
import com.bonusgaming.battleofmindskotlin.core.main.dto.UrlSticker
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import io.reactivex.Single
import java.io.FileOutputStream
import javax.inject.Inject

private const val BITMAP_QUALITY=100

@PerFeature
class LoadingAssetsRepository @Inject constructor(private val stickersDao: StickerDao,
                                                  private val webRepo: WebApi,
                                                  private val picasso: Picasso,
                                                  private val pathProvider: PathProvider) {

    //strong reference for ImageTarget
    private val listImageTarget = mutableMapOf<String, ImageTarget>()

    //получаем список url через rest api
    fun getStickerUrls(): Single<List<UrlSticker>> {
        return webRepo.provideStickerApi().getStickers("").map {
            val resultList = mutableListOf<UrlSticker>()
            it.forEach { item ->
                if (item.size.toInt() > 0) resultList.add(item)
            }
            resultList
        }
    }

    fun addStickerToDb(sticker: Sticker) {
        stickersDao.insertAll(sticker)
    }

    fun saveBitmapToDisk(sticker: Sticker, bitmap: Bitmap) {
        val outputStream = FileOutputStream(pathProvider.getImagesPath() + sticker.path)
        bitmap.compress(Bitmap.CompressFormat.PNG, BITMAP_QUALITY, outputStream)
    }

    fun getHashStickersList() = stickersDao.getHashStickersList()

    //скачиваем картинку через Picasso
    fun downloadUrlStickerToDisk(
            urlSticker: UrlSticker,
            onDownload: (bitmap: Bitmap) -> Unit,
            onException: (url: String) -> Unit
    ) {
        val imageTarget = ImageTarget(urlSticker.name, onDownload, onException)
        listImageTarget[urlSticker.mediaLink] = imageTarget
        picasso.load(urlSticker.mediaLink)
                .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                .into(imageTarget)
    }

    /*
    повторить загрузку через afterMilliseconds
    миллисекунд для url, для задержки используется Handler
    */
    fun retryDownload(sticker: UrlSticker, afterMilliseconds: Long) {
        val handler = Handler()
        handler.postDelayed({
            listImageTarget[sticker.mediaLink]?.let {
                picasso.load(sticker.mediaLink)
                        .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                        .into(it)
            }
        }, afterMilliseconds)
    }

    fun isAvatarCreated() = webRepo.provideFirebaseAuth().currentUser != null
}
