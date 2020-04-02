package com.bonusgaming.battleofmindskotlin.features.creating_avatar

import com.bonusgaming.battleofmindskotlin.base_db_api.AvatarDao
import com.bonusgaming.battleofmindskotlin.base_db_api.StickerDao
import com.bonusgaming.battleofmindskotlin.core.main.di.scope.PerFeature
import com.bonusgaming.battleofmindskotlin.core.main.dto.Avatar
import com.bonusgaming.battleofmindskotlin.core.main.dto.Sticker
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

@PerFeature
class CreatingAvatarModel @Inject constructor(
        private val avatarDao: AvatarDao,
        private val stickersDao: StickerDao) {

    fun saveAvatar(avatar: Avatar) {
        avatarDao.insert(avatar)
    }

    fun getAvatar(): Avatar = avatarDao.getAvatar()
    fun getMonsters(): List<Sticker> = stickersDao.getMonsters()
    //todo вынести FirebasetAuth to WebImpl module
    fun getUserUid() = FirebaseAuth.getInstance().currentUser?.uid
}