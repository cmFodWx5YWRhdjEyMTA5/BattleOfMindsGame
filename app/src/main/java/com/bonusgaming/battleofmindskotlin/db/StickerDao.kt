package com.bonusgaming.battleofmindskotlin.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Observable


@Dao
interface StickerDao {

    companion object {
        const val stickers = "stickers"
    }

    @Insert
    fun insertAll(vararg users: StickerEntry)

    @Query("SELECT * FROM $stickers")
    fun getListStickers(): List<StickerEntry>

    @Query("SELECT * FROM $stickers")
    fun getAll(): List<StickerEntry>

    @Query("SELECT * FROM $stickers where path like '%face%'")
    fun getAllFaces(): List<StickerEntry>

    @Query("SELECT * FROM $stickers where path like '%' || :shape || '%' collate nocase")
    fun getBodiesByShape(shape: String): List<StickerEntry>

    @Query("SELECT * FROM $stickers where usedForAvatar = 1")
    fun getUsedForAvatar(): List<StickerEntry>

    @Query("SELECT hashMD5 FROM $stickers")
    fun getHashStickersList(): List<String>

    @Query("SELECT path FROM $stickers")
    fun getPathStickersList(): List<String>

    @Query("SELECT * FROM $stickers where hashMD5=:hashMD5")
    fun getStickersByHash(hashMD5: String): Observable<List<StickerEntry>>
}