package com.bonusgaming.battleofmindskotlin.db

import androidx.room.PrimaryKey


open class BaseEntity {

    lateinit var question: String
    @PrimaryKey
    var id: Int = 0
    lateinit var answer_1: String
    lateinit var answer_2: String
    lateinit var answer_3: String
    lateinit var answer_4: String

    fun copy(): BaseEntity {
        val newEntity = BaseEntity()
        newEntity.question = this.question
        newEntity.answer_1 = this.answer_1
        newEntity.answer_2 = this.answer_2
        newEntity.answer_3 = this.answer_3
        newEntity.answer_4 = this.answer_4
        newEntity.id = this.id
        return newEntity
    }
}