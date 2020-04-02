package com.bonusgaming.battleofmindskotlin.dbimpl.adapter

import android.util.Log
import com.bonusgaming.battleofmindskotlin.dbapi.AvatarDao
import com.bonusgaming.battleofmindskotlin.dbimpl.AvatarEntry
import com.bonusgaming.battleofmindskotlin.dbimpl.AvatarEntryDao
import com.bonusgaming.battleofmindskotlin.core.main.dto.Avatar

class ToAvatarDaoAdapter(private val avatarEntryDao: AvatarEntryDao) : AvatarDao {
    override fun insert(avatar: Avatar) {
        Log.e("5454", "insert ${avatar.idSticker}")
        Log.e("5454", "insert ${avatar.nickName}")
        val avatarEntry = AvatarEntry(avatar.nickName, avatar.idSticker, avatar.idFirebase)
        avatarEntryDao.insert(avatarEntry)
    }

    override fun getAvatar(): Avatar {
        val avatarEntry = avatarEntryDao.getAvatar()
        return Avatar(avatarEntry.nickName, avatarEntry.idSticker, avatarEntry.idFirebase)
    }
}
