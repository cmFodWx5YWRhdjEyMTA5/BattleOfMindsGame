package com.bonusgaming.battleofmindskotlin.di.module

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.bonusgaming.battleofmindskotlin.db.Database
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

const val DB_NAME = "db"
const val LOCAL_DB_NAME = "mainarenasqlite"

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun getDatabase(context: Context): Database {
        return Room.databaseBuilder(context, Database::class.java, DB_NAME).build()
    }

}