package com.bonusgaming.battleofmindskotlin.dbimpl

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface AvatarEntryDao {

    @Insert
    fun insert(avatar: AvatarEntry)

    @Query("SELECT * FROM $AVATAR_TABLE_NAME")
    fun getAvatar(): AvatarEntry

}