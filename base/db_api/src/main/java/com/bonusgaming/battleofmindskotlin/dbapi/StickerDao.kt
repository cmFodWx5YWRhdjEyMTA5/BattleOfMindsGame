package com.bonusgaming.battleofmindskotlin.dbapi

import com.bonusgaming.battleofmindskotlin.core.main.dto.Sticker
import io.reactivex.Observable

interface StickerDao {

    fun insertAll(vararg stickers: Sticker)

    fun getListStickers(): List<Sticker>

    fun getMonsters(): List<Sticker>

    fun getHashStickersList(): List<String>

    fun getPathStickersList(): List<String>

    fun getStickersByHash(hashMD5: String): Observable<List<Sticker>>

    fun getStickersById(id: Int): Sticker

}
