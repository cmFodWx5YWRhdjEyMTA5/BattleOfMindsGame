package com.bonusgaming.battleofmindskotlin.base_db_impl

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bonusgaming.battleofmindskotlin.core.main.dto.UserInfo

@Entity(tableName = "avatar")
data class AvatarEntry(
        @Embedded val userInfo: UserInfo,
        val idSticker: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
