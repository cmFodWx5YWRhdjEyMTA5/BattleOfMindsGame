package com.bonusgaming.battleofmindskotlin.base_db_impl.adapter

import android.util.Log
import com.bonusgaming.battleofmindskotlin.base_db_api.AvatarDao
import com.bonusgaming.battleofmindskotlin.base_db_impl.AvatarEntry
import com.bonusgaming.battleofmindskotlin.base_db_impl.AvatarEntryDao
import com.bonusgaming.battleofmindskotlin.core.main.dto.Avatar

class ToAvatarDaoAdapter(private val avatarEntryDao: AvatarEntryDao) : AvatarDao {
    override fun insert(avatar: Avatar) {
        Log.e("5454","insert ${avatar.idSticker}")
        Log.e("5454","insert ${avatar.nickName}")
        val avatarEntry = AvatarEntry(avatar)
        avatarEntryDao.insert(avatarEntry)
    }

    override fun getAvatar(): Avatar {
        return avatarEntryDao.getAvatar().avatar
    }
}