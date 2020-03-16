package com.bonusgaming.battleofmindskotlin

import android.content.Context
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PathProvider @Inject constructor(val context: Context) {

    fun getImagesPath(): String {
        val result = context.filesDir.path + "/images/"

        val directory = File(result)
        if (!directory.exists()) directory.mkdirs()
        return result
    }
}