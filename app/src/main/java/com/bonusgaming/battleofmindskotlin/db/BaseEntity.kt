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
        return BaseEntity().also {
            it.question = this.question
            it.answer_1 = this.answer_1
            it.answer_2 = this.answer_2
            it.answer_3 = this.answer_3
            it.answer_4 = this.answer_4
            it.id = this.id
        }
    }
}