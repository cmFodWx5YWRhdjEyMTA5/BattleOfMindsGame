package com.bonusgaming.battleofmindskotlin.base_db_impl.di.module

import android.content.Context

import androidx.room.Room
import com.bonusgaming.battleofmindskotlin.base_db_api.AvatarDao
import com.bonusgaming.battleofmindskotlin.base_db_api.StickerDao
import com.bonusgaming.battleofmindskotlin.base_db_impl.AvatarEntryDao
import com.bonusgaming.battleofmindskotlin.base_db_impl.Database
import com.bonusgaming.battleofmindskotlin.base_db_impl.StickerEntryDao
import com.bonusgaming.battleofmindskotlin.base_db_impl.adapter.ToAvatarDaoAdapter
import com.bonusgaming.battleofmindskotlin.base_db_impl.adapter.ToStickerDaoAdapter
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