package com.bonusgaming.battleofmindskotlin.base_db_impl

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bonusgaming.battleofmindskotlin.core.main.dto.Sticker

@Entity(tableName = "stickers")
data class StickerEntry(
        @Embedded val sticker: Sticker
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}