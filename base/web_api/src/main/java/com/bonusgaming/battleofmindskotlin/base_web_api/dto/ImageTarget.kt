package com.bonusgaming.battleofmindskotlin.base_web_api.dto

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

/*
Класс, реализующий интерфейс Target для
Picasso, где мы сохраняем картинку в нужное нам
место и вызываем колбэки для ошибок и успеха:
doOnException() и doOnDownload()
 */
class ImageTarget(
        private val fileName: String,
        private val doOnDownload: (bitmap: Bitmap) -> Unit,
        private val doOnException: (fileName: String) -> Unit
) : Target {

    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
        Log.e("ImageTarget", "onPrepareLoad")
    }

    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
        Log.e("ImageTarget", "onBitmapFailed exception $e")
        doOnException(fileName)
    }

    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
        Log.e("ImageTarget", "onBitmapLoaded")
        bitmap?.let {
            doOnDownload(it)
        }
    }
}
