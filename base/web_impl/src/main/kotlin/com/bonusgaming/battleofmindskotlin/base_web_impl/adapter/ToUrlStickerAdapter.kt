package com.bonusgaming.battleofmindskotlin.base_web_impl.adapter

import com.bonusgaming.battleofmindskotlin.base_web_api.StickerApi
import com.bonusgaming.battleofmindskotlin.base_web_impl.CloudStorageItem
import com.bonusgaming.battleofmindskotlin.base_web_impl.StickersApiRetrofit
import com.bonusgaming.battleofmindskotlin.core.main.dto.UrlSticker
import io.reactivex.Single

class ToUrlStickerAdapter(val stickersApiRetrofit: StickersApiRetrofit) : StickerApi {

    override fun getStickers(prefix: String): Single<List<UrlSticker>> {
        return stickersApiRetrofit
                .getListUrls(prefix)
                .map { single -> single.items.map { UrlSticker(it.mediaLink, it.name, it.size, it.md5Hash) } }
    }
}