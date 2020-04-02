package com.bonusgaming.battleofmindskotlin.dbimpl

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Observable

@Dao
interface StickerEntryDao {

    @Insert
    fun insertAll(vararg stickers: StickerEntry)

    @Query("SELECT * FROM $STICKERS_TABLE_NAME")
    fun getListStickers(): List<StickerEntry>

    @Query("SELECT * FROM $STICKERS_TABLE_NAME where path like '%$MONSTER_PREFIX%'")
    fun getMonsters(): List<StickerEntry>

    @Query("SELECT hashMD5 FROM $STICKERS_TABLE_NAME")
    fun getHashStickersList(): List<String>

    @Query("SELECT path FROM $STICKERS_TABLE_NAME")
    fun getPathStickersList(): List<String>

    @Query("SELECT * FROM $STICKERS_TABLE_NAME where hashMD5=:hashMD5")
    fun getStickersByHash(hashMD5: String): Observable<List<StickerEntry>>

    @Query("SELECT * FROM $STICKERS_TABLE_NAME where id=:id")
    fun getStickersById(id: Int): StickerEntry
}
