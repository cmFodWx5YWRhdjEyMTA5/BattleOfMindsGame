package com.bonusgaming.battleofmindskotlin.base_db_impl

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bonusgaming.battleofmindskotlin.core.main.contract.UserInfoContract

@Entity(tableName = AVATAR_TABLE_NAME)
data class
AvatarEntry(override val nickName: String,
            override val idSticker: Int,
            override val idFirebase: String)
    : UserInfoContract {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
