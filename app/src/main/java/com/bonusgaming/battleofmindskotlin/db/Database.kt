package com.bonusgaming.battleofmindskotlin.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [HistoryEntity::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}