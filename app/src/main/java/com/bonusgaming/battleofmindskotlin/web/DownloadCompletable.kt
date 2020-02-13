package com.bonusgaming.battleofmindskotlin.web

import android.net.Uri
import java.lang.Exception

interface DownloadCompletable<T> {
    fun onComplete(result: List<T>)
    fun onFailure(result: Exception)
}