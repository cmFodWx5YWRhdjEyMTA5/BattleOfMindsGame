package com.bonusgaming.battleofmindskotlin.creating_avatar

import androidx.room.Database
import javax.inject.Inject

class CreatingAvatarModel @Inject constructor() {

    @Inject
    lateinit var database: Database

    fun inflateAvatar(avatar: Avatar) {

    }
}