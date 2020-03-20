package com.bonusgaming.battleofmindskotlin.base_db_impl

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

internal const val avatarTable = "avatar"

@Dao
interface AvatarDao {

    @Insert
    fun insert(avatar: AvatarEntry)

    @Query("SELECT * FROM $avatarTable")
    fun getAvatar(): AvatarEntry

}