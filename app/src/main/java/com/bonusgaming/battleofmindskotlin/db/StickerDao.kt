package com.bonusgaming.battleofmindskotlin.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bonusgaming.battleofmindskotlin.BuildConfig
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

    @Query("SELECT * FROM $stickers where path like '%${BuildConfig.PREFIX_MONSTER}%'")
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