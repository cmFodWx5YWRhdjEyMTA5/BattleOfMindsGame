package com.bonusgaming.battleofmindskotlin.core.main

import android.content.Context
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton


class PathProvider @Inject constructor(private val context: Context) {

    fun getImagesPath(): String {
        val result = context.filesDir.path + "/images/"

        val directory = File(result)
        if (!directory.exists()) directory.mkdirs()
        return result
    }
}