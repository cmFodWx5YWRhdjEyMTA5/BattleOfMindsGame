package com.bonusgaming.battleofmindskotlin.dbimpl.adapter

import com.bonusgaming.battleofmindskotlin.dbapi.StickerDao
import com.bonusgaming.battleofmindskotlin.dbimpl.StickerEntry
import com.bonusgaming.battleofmindskotlin.dbimpl.StickerEntryDao
import com.bonusgaming.battleofmindskotlin.core.main.dto.Sticker
import io.reactivex.Observable

class ToStickerDaoAdapter(private val stickerEntryDao: StickerEntryDao) : StickerDao {

    @Suppress("SpreadOperator")
    override fun insertAll(vararg stickers: Sticker) {
        val toStickersEntry = stickers.map { StickerEntry(it.hashMD5, it.path) }.toTypedArray()
        stickerEntryDao.insertAll(*toStickersEntry)
    }

    override fun getListStickers(): List<Sticker> {
        return stickerEntryDao.getListStickers().map { it.toSticker() }
    }

    override fun getMonsters(): List<Sticker> {
        return stickerEntryDao.getMonsters().map { it.toSticker() }
    }

    override fun getHashStickersList(): List<String> {
        return stickerEntryDao.getHashStickersList()
    }

    override fun getPathStickersList(): List<String> {
        return stickerEntryDao.getPathStickersList()
    }

    override fun getStickersByHash(hashMD5: String): Observable<List<Sticker>> {
        return stickerEntryDao.getStickersByHash(hashMD5).map { observable -> observable.map { it.toSticker() } }
    }

    override fun getStickersById(id: Int): Sticker {
        return stickerEntryDao.getStickersById(id).toSticker()
    }

    private fun StickerEntry.toSticker(): Sticker {
        return Sticker(hashMD5, path).also { it.id = id }
    }
}
