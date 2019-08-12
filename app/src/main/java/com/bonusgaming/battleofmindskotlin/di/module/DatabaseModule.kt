package com.bonusgaming.battleofmindskotlin.di.module

import android.content.Context
import androidx.room.Room
import com.bonusgaming.battleofmindskotlin.db.Database
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

const val DB_NAME = "db"
const val LOCAL_DB_NAME = "mainarenasqlite"

@Module
class DatabaseModule {


    private fun copyIfNeeded(context: Context) {
        if (!context.getDatabasePath(DB_NAME).exists()) {
            val inputStream = context.assets.open(LOCAL_DB_NAME)
            val outputStream = context.getDatabasePath(DB_NAME).outputStream()
            outputStream.write(inputStream.readBytes())
        }
    }

    @Singleton
    @Provides
    fun getDatabase(context: Context): Database {
        copyIfNeeded(context)
        return Room.databaseBuilder(context, Database::class.java, DB_NAME).build()
    }

}