package com.bonusgaming.battleofmindskotlin.features.login.creating_avatar

import com.bonusgaming.battleofmindskotlin.base_db_api.AvatarDao
import com.bonusgaming.battleofmindskotlin.base_db_api.StickerDao
import com.bonusgaming.battleofmindskotlin.core.main.di.scope.PerFragment
import com.bonusgaming.battleofmindskotlin.core.main.dto.Avatar
import com.bonusgaming.battleofmindskotlin.core.main.dto.Sticker
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

@PerFragment
class CreatingAvatarModel @Inject constructor(
        private val avatarDao: AvatarDao,
        private val stickersDao: StickerDao) {

    fun saveAvatar(avatar: Avatar) {
        avatarDao.insert(avatar)
    }

    fun getAvatar(): Avatar = avatarDao.getAvatar()
    fun getMonsters(): List<Sticker> = stickersDao.getMonsters()
    fun getUserUid() = FirebaseAuth.getInstance().currentUser?.uid
}