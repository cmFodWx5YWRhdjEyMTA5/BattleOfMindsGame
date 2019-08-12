package com.bonusgaming.battleofmindskotlin.db

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable

const val history = "history"

@Dao
interface HistoryDao {

    @Query("SELECT * FROM $history WHERE id == abs(random()) % (SELECT max(rowid) FROM $history) + 1")
    fun getRandom(): Flowable<HistoryEntity>
}