package com.bonusgaming.battleofmindskotlin.creating_avatar

import com.bonusgaming.battleofmindskotlin.PathProvider
import com.bonusgaming.battleofmindskotlin.db.AvatarDao
import com.bonusgaming.battleofmindskotlin.db.AvatarEntry
import com.bonusgaming.battleofmindskotlin.db.StickerDao
import com.bonusgaming.battleofmindskotlin.db.StickerEntry
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import java.io.File
import javax.inject.Inject

class CreatingAvatarModel @Inject constructor(val pathProvider: PathProvider) {

    @Inject
    lateinit var avatarDao: AvatarDao

    @Inject
    lateinit var stickersDao: StickerDao

    @Inject
    lateinit var picasso: Picasso

    fun inflateAvatar(avatar: Avatar) {
        picasso.load(File(pathProvider.getImagesPath() + avatar.pathMonster))
                .into(avatar.monsterImageView)
    }

    fun saveAvatar(avatar: AvatarEntry) {
        avatarDao.insert(avatar)
    }

    fun getAvatar(): Observable<AvatarEntry> = avatarDao.getAvatar()
    fun getMonsters(): List<StickerEntry> = stickersDao.getMonsters()
    fun getUserUid() = FirebaseAuth.getInstance().currentUser?.uid
}