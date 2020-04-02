package com.bonusgaming.battleofmindskotlin.webimpl.adapter

import com.bonusgaming.battleofmindskotlin.webapi.StickerApi
import com.bonusgaming.battleofmindskotlin.webimpl.StickersApiRetrofit
import com.bonusgaming.battleofmindskotlin.core.main.dto.UrlSticker
import io.reactivex.Single

class ToUrlStickerAdapter(val stickersApiRetrofit: StickersApiRetrofit) : StickerApi {

    override fun getStickers(prefix: String): Single<List<UrlSticker>> {
        return stickersApiRetrofit
                .getListUrls(prefix)
                .map { single -> single.items.map { UrlSticker(it.mediaLink, it.name, it.size, it.md5Hash) } }
    }
}
