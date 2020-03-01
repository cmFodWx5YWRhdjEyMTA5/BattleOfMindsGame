package com.bonusgaming.battleofmindskotlin.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Observable
import io.reactivex.Single


@Dao
interface AvatarDao {

    companion object {
        const val avatarTable = "avatar"
    }

    @Insert
    fun insert(avatar: AvatarEntry)

    @Query("SELECT * FROM $avatarTable")
    fun getAvatar(): Observable<AvatarEntry>

}