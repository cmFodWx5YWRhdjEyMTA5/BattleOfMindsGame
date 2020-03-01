package com.bonusgaming.battleofmindskotlin.creating_avatar

import android.util.Log
import com.bonusgaming.battleofmindskotlin.PathProvider
import com.bonusgaming.battleofmindskotlin.db.AvatarEntry
import com.bonusgaming.battleofmindskotlin.db.Database
import com.bonusgaming.battleofmindskotlin.db.StickerEntry
import com.bonusgaming.battleofmindskotlin.web.FirestoreProvider
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.Single
import java.io.File
import javax.inject.Inject

class CreatingAvatarModel @Inject constructor() {

    @Inject
    lateinit var firebaseDatabaseProvider: FirestoreProvider

    @Inject
    lateinit var database: Database

    @Inject
    lateinit var pathProvider: PathProvider

    @Inject
    lateinit var picasso: Picasso

    fun inflateAvatar(avatar: Avatar) {
        Log.e("avatar", "inflateavatar")
        picasso.load(File(pathProvider.getImagesPath() + avatar.pathMonster))
                .into(avatar.monsterImageView)
    }

    fun saveAvatar(avatar: AvatarEntry) {
        database.avatarDao().insert(avatar)
    }

    fun getAvatar(): Observable<AvatarEntry> {
        return database.avatarDao().getAvatar()
    }

    fun getMonsters(): List<StickerEntry> {
        return database.stickersDao().getMonsters()
    }

    fun getUserUid() = FirebaseAuth.getInstance().currentUser?.uid
}