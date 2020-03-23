package com.bonusgaming.battleofmindskotlin.base_web_api

import com.bonusgaming.battleofmindskotlin.core.main.dto.UrlSticker
import io.reactivex.Single

interface StickerApi {
    fun getStickers(prefix:String): Single<List<UrlSticker>>
}