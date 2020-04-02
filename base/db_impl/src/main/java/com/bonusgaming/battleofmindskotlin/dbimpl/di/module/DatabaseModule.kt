package com.bonusgaming.battleofmindskotlin.dbimpl.di.module

import android.content.Context

import androidx.room.Room
import com.bonusgaming.battleofmindskotlin.dbapi.AvatarDao
import com.bonusgaming.battleofmindskotlin.dbapi.StickerDao
import com.bonusgaming.battleofmindskotlin.dbimpl.Database
import com.bonusgaming.battleofmindskotlin.dbimpl.adapter.ToAvatarDaoAdapter
import com.bonusgaming.battleofmindskotlin.dbimpl.adapter.ToStickerDaoAdapter
import com.bonusgaming.battleofmindskotlin.core.main.di.scope.PerFacade
import dagger.Module
import dagger.Provides


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

    @PerFacade
    @Provides
    fun getStickersDao(context: Context): StickerDao {
        initDatabase(context)
        return ToStickerDaoAdapter(database.stickersDao())
    }

    @PerFacade
    @Provides
    fun getAvatarDao(context: Context): AvatarDao {
        initDatabase(context)
        return ToAvatarDaoAdapter(database.avatarDao())
    }
}
