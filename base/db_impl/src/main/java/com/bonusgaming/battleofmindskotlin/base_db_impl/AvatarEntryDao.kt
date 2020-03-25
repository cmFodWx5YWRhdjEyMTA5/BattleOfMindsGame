package com.bonusgaming.battleofmindskotlin.base_db_impl

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bonusgaming.battleofmindskotlin.base_db_api.AvatarDao

internal const val avatarTable = "avatar"

@Dao
interface AvatarEntryDao {

    @Insert()
    fun insert(avatar: AvatarEntry)


    @Query("SELECT * FROM $avatarTable")
    fun getAvatar(): AvatarEntry

}