package com.bonusgaming.battleofmindskotlin.base_db_impl

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bonusgaming.battleofmindskotlin.core.main.dto.Avatar

@Entity(tableName = "avatar")
data class AvatarEntry(
        @Embedded val avatar: Avatar
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
