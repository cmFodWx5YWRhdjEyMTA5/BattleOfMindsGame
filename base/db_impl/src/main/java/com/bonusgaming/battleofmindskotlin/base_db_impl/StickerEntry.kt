package com.bonusgaming.battleofmindskotlin.base_db_impl

import androidx.room.Embedded
import androidx.room.Entity
import com.bonusgaming.battleofmindskotlin.core.main.dto.Sticker

@Entity(tableName = STICKERS_TABLE_NAME,
        primaryKeys = [STICKERS_PRIMARY_KEY])
data class StickerEntry(
        @Embedded val sticker: Sticker
)