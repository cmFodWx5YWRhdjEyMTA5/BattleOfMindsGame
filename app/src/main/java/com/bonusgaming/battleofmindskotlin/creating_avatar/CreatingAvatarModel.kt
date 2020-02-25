package com.bonusgaming.battleofmindskotlin.creating_avatar

import android.util.Log
import com.bonusgaming.battleofmindskotlin.PathProvider
import com.bonusgaming.battleofmindskotlin.db.Database
import com.bonusgaming.battleofmindskotlin.db.StickerEntry
import com.squareup.picasso.Picasso
import java.io.File
import javax.inject.Inject

class CreatingAvatarModel @Inject constructor() {

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

    fun getBodies(): List<StickerEntry> {
        return database.stickersDao().getBodies()
    }

    fun getEyes(): List<StickerEntry> {
        return database.stickersDao().getEyes()
    }

    fun getMouths(): List<StickerEntry> {
        return database.stickersDao().getMouths()
    }
    fun getMonsters(): List<StickerEntry> {
        return database.stickersDao().getMonsters()
    }
}