package com.bonusgaming.battleofmindskotlin.webapi

import com.bonusgaming.battleofmindskotlin.core.main.dto.UrlSticker
import io.reactivex.Single

interface StickerApi {
    fun getStickers(prefix:String): Single<List<UrlSticker>>
}
