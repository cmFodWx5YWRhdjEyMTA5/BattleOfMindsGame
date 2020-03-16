package com.bonusgaming.battleofmindskotlin.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "avatar")
data class AvatarEntry(
        val nickName: String,
        val idSticker: Int,
        val idFirebase: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
