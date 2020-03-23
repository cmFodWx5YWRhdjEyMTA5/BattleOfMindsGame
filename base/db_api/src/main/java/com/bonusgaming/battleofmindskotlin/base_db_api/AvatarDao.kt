package com.bonusgaming.battleofmindskotlin.base_db_api

import com.bonusgaming.battleofmindskotlin.core.main.dto.Avatar

interface AvatarDao {
    fun insert(avatar: Avatar)

    fun getAvatar(): Avatar
}