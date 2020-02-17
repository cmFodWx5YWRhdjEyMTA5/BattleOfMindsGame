package com.bonusgaming.battleofmindskotlin.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import io.reactivex.Flowable
import io.reactivex.Observable
import java.util.zip.CRC32


@Dao
interface StickerDao {

    companion object {
        const val stickers = "stickers"
    }

    @Insert
    fun insertAll(vararg users: StickerEntry)

    @Query("SELECT * FROM $stickers")
    fun getListStickers(): List<StickerEntry>

    @Query("SELECT hashMD5 FROM $stickers")
    fun getHashStickersList(): List<String>

    @Query("SELECT path FROM $stickers")
    fun getPathStickersList(): List<String>

    @Query("SELECT * FROM $stickers where hashMD5=:hashMD5")
    fun getStickersByHash(hashMD5: String): Observable<List<StickerEntry>>
}