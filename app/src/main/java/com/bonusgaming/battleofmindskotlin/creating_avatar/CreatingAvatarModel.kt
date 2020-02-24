package com.bonusgaming.battleofmindskotlin.creating_avatar

import android.util.Log
import com.bonusgaming.battleofmindskotlin.AvatarShape
import com.bonusgaming.battleofmindskotlin.PathProvider
import com.bonusgaming.battleofmindskotlin.db.Database
import com.bonusgaming.battleofmindskotlin.db.StickerEntry
import com.squareup.picasso.Picasso
import java.io.File
import java.util.*
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
        Log.e("avatar", "(avatar.bodyImageView ${avatar.pathFace}")
        Log.e("avatar", "(avatar.faceImageView ${avatar.pathBody}")
        picasso.load(File(pathProvider.getImagesPath() + avatar.pathBody))
            .into(avatar.bodyImageView)
        picasso.load(File(pathProvider.getImagesPath() + avatar.pathFace))
            .into(avatar.faceImageView)
    }

    fun getAllFaces(): List<StickerEntry> {
        return database.stickersDao().getAllFaces()
    }

    fun getBodiesByShape(): Map<AvatarShape, List<StickerEntry>> {
        val map = mutableMapOf<AvatarShape, List<StickerEntry>>()
       /* EnumSet.allOf(AvatarShape::class.java).forEach {
            val list = database.stickersDao().getBodiesByShape(it.name)
            Log.e("getBodiesByShape","list size ${list.size}")
            map[it] = list
        }*/

        val list = database.stickersDao().getBodiesByShape(AvatarShape.FLUFFY.name)
     //   Log.e("getBodiesByShape","list size ${list.size}")
        map[AvatarShape.FLUFFY] = list
        return map
    }
}