package com.bonusgaming.battleofmindskotlin.base_db_impl

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [StickerEntry::class, AvatarEntry::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {
    abstract fun stickersDao(): StickerEntryDao
    abstract fun avatarDao(): AvatarEntryDao
}