package com.bonusgaming.battleofmindskotlin.loading_assets

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import com.bonusgaming.battleofmindskotlin.App
import com.bonusgaming.battleofmindskotlin.PathProvider
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.io.FileOutputStream
import javax.inject.Inject

class ImageTarget(
    private val fileName: String,
    private val doOnDownload: () -> Unit,
    private val doOnException: (fileName: String) -> Unit
) : Target {

    @Inject
    lateinit var pathProvider: PathProvider

    init {
        App.appComponent.inject(this)
    }

    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
        Log.e("ImageTarget", "onPrepareLoad")
    }

    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
        Log.e("ImageTarget", "onBitmapFailed exception $e")
        doOnException(fileName)
    }

    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
        Log.e("ImageTarget", "onBitmapLoaded")
        val outputStream = FileOutputStream(pathProvider.getImagesPath() + fileName)
        bitmap?.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        doOnDownload()
    }
}
