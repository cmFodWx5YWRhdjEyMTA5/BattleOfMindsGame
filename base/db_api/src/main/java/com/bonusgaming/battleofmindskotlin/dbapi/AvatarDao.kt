package com.bonusgaming.battleofmindskotlin.dbapi

import com.bonusgaming.battleofmindskotlin.core.main.dto.Avatar

interface AvatarDao {
    fun insert(avatar: Avatar)

    fun getAvatar(): Avatar
}
