package com.bonusgaming.battleofmindskotlin.login.creating_avatar

import com.bonusgaming.battleofmindskotlin.PathProvider
import com.bonusgaming.battleofmindskotlin.db.AvatarDao
import com.bonusgaming.battleofmindskotlin.db.AvatarEntry
import com.bonusgaming.battleofmindskotlin.db.StickerDao
import com.bonusgaming.battleofmindskotlin.db.StickerEntry
import com.bonusgaming.battleofmindskotlin.di.scope.PerFragment
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import javax.inject.Inject

@PerFragment
class CreatingAvatarModel @Inject constructor(
        private val avatarDao: AvatarDao,
        private val stickersDao: StickerDao) {

    fun saveAvatar(avatar: AvatarEntry) {
        avatarDao.insert(avatar)
    }

    fun getAvatar(): AvatarEntry = avatarDao.getAvatar()
    fun getMonsters(): List<StickerEntry> = stickersDao.getMonsters()
    fun getUserUid() = FirebaseAuth.getInstance().currentUser?.uid
}