package com.bonusgaming.battleofmindskotlin.di.module

import android.content.Context
import androidx.room.Room
import com.bonusgaming.battleofmindskotlin.db.AvatarDao
import com.bonusgaming.battleofmindskotlin.db.Database
import com.bonusgaming.battleofmindskotlin.db.StickerDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DatabaseModule {

    companion object {
        const val DB_NAME = "maindb"
    }

    private lateinit var database: Database

    private fun initDatabase(context: Context) {
        if (!::database.isInitialized)
            database = Room.databaseBuilder(context, Database::class.java, DB_NAME).build()
    }

    @Singleton
    @Provides
    fun getStickersDao(context: Context): StickerDao {
        initDatabase(context)
        return database.stickersDao()
    }

    @Singleton
    @Provides
    fun getAvatarDao(context: Context): AvatarDao {
        initDatabase(context)
        return database.avatarDao()
    }
}