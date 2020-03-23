package com.bonusgaming.battleofmindskotlin.base_db_impl.adapter

import com.bonusgaming.battleofmindskotlin.base_db_api.StickerDao
import com.bonusgaming.battleofmindskotlin.base_db_impl.StickerEntry
import com.bonusgaming.battleofmindskotlin.base_db_impl.StickerEntryDao
import com.bonusgaming.battleofmindskotlin.core.main.dto.Sticker
import io.reactivex.Observable

class ToStickerDaoAdapter(private val stickerEntryDao: StickerEntryDao) : StickerDao {
    override fun insertAll(vararg stickers: Sticker) {
        stickerEntryDao.insertAll(*stickers.map { StickerEntry(it) }.toTypedArray())
    }

    override fun getListStickers(): List<Sticker> {
        return stickerEntryDao.getListStickers().map { it.sticker }
    }

    override fun getMonsters(): List<Sticker> {
        return stickerEntryDao.getMonsters().map { it.sticker }
    }

    override fun getHashStickersList(): List<String> {
        return stickerEntryDao.getHashStickersList()
    }

    override fun getPathStickersList(): List<String> {
        return stickerEntryDao.getPathStickersList()
    }

    override fun getStickersByHash(hashMD5: String): Observable<List<Sticker>> {
        return stickerEntryDao.getStickersByHash(hashMD5).map { observable -> observable.map { it.sticker } }
    }

    override fun getStickersById(id: Int): Sticker {
        return stickerEntryDao.getStickersById(id).sticker
    }

}