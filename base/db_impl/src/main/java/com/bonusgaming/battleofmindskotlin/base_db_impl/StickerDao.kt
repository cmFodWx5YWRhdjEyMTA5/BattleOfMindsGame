package com.bonusgaming.battleofmindskotlin.base_db_impl

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Observable

internal const val stickers = "stickers"
internal const val monsters = "monsters"

@Dao
interface StickerDao {

    @Insert
    fun insertAll(vararg stickers: StickerEntry)

    @Query("SELECT * FROM $stickers")
    fun getListStickers(): List<StickerEntry>

    @Query("SELECT * FROM $stickers")
    fun getAll(): List<StickerEntry>

    @Query("SELECT * FROM $stickers where path like '%$monsters%'")
    fun getMonsters(): List<StickerEntry>

    @Query("SELECT hashMD5 FROM $stickers")
    fun getHashStickersList(): List<String>

    @Query("SELECT path FROM $stickers")
    fun getPathStickersList(): List<String>

    @Query("SELECT * FROM $stickers where hashMD5=:hashMD5")
    fun getStickersByHash(hashMD5: String): Observable<List<StickerEntry>>

    @Query("SELECT * FROM $stickers where id=:id")
    fun getStickersById(id: Int): StickerEntry
}