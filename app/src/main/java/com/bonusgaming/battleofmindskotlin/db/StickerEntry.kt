package com.bonusgaming.battleofmindskotlin.db

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "stickers")
data class StickerEntry(
    val hashMD5: String,
    val path: String
){
    @PrimaryKey(autoGenerate = true)
    var id: Int=0
}