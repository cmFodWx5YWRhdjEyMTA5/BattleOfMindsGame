package com.bonusgaming.battleofmindskotlin.base_db_impl

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bonusgaming.battleofmindskotlin.core.main.contract.StickerInfoContract

@Entity(tableName = STICKERS_TABLE_NAME)
data class StickerEntry(override val hashMD5: String, override val path: String) : StickerInfoContract {
    @PrimaryKey(autoGenerate = true)
    override var id: Int = 0
}